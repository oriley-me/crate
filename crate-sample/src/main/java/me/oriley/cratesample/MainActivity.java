package me.oriley.cratesample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import me.oriley.crate.Crate;
import me.oriley.crate.FontAsset;
import me.oriley.crate.ImageAsset;
import me.oriley.crate.SvgAsset;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;

    private RecyclerView mRecyclerView;

    private Crate mCrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrate = new Crate.Builder(this)
                .setTypefaceCacheEnabled(true)
                .setBitmapCacheEnabled(true)
                .setSvgCacheEnabled(true)
                .build();

        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        assert mRecyclerView != null;
        mRecyclerView.setHasFixedSize(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //noinspection deprecation
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.setCheckedItem(R.id.nav_fonts);
        }
        onNavigationItemSelected(R.id.nav_fonts);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRecyclerView.setAdapter(null);
        mCrate.clear();
        mCrate = null;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return onNavigationItemSelected(item.getItemId());
    }

    private boolean onNavigationItemSelected(@IdRes int id) {
        boolean scrollAdapter = false;
        if (id == R.id.nav_fonts) {
            scrollAdapter = true;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(new FontRecyclerAdapter(mCrate));
        } else if (id == R.id.nav_images) {
            scrollAdapter = true;
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            mRecyclerView.setAdapter(new BitmapRecyclerAdapter(mCrate));
        } else if (id == R.id.nav_svgs) {
            scrollAdapter = true;
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            mRecyclerView.setAdapter(new SvgRecyclerAdapter(mCrate));
        } else if (id == R.id.nav_info) {
            showInfoDialog();
        }

        if (scrollAdapter) {
            mRecyclerView.scrollToPosition(Integer.MAX_VALUE / 2);
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showInfoDialog() {
        new AlertDialog.Builder(this)
                .setTitle(String.format("%s %s", getString(R.string.app_name), BuildConfig.VERSION_NAME))
                .setMessage(R.string.info_dialog)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private static final class FontViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        TextView textView;

        FontViewHolder(@NonNull View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.text_view);
        }
    }

    private static final class FontRecyclerAdapter extends RecyclerView.Adapter<FontViewHolder> {

        @NonNull
        private final Crate mCrate;

        private final int mActualSize;

        FontRecyclerAdapter(@NonNull Crate crate) {
            mCrate = crate;
            mActualSize = mCrate.assets.fonts.LIST.size();
        }

        @Override
        public FontViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.font_view_item, viewGroup, false);
            return new FontViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FontViewHolder holder, int position) {
            FontAsset fontAsset = mCrate.assets.fonts.LIST.get(position % mActualSize);
            holder.textView.setText(fontAsset.getFontName());
            holder.textView.setTypeface(mCrate.getTypeface(fontAsset));
        }

        @Override
        public int getItemCount() {
            return Integer.MAX_VALUE;
        }
    }

    private static final class BitmapViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        ImageView imageView;

        BitmapViewHolder(@NonNull View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image_view);
        }
    }

    private static final class BitmapRecyclerAdapter extends RecyclerView.Adapter<BitmapViewHolder> {

        @NonNull
        private final Crate mCrate;

        private final int mActualSize;

        BitmapRecyclerAdapter(@NonNull Crate crate) {
            mCrate = crate;
            mActualSize = mCrate.assets.images.LIST.size();
        }

        @Override
        public BitmapViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.bitmap_view_item, viewGroup, false);
            return new BitmapViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BitmapViewHolder holder, int position) {
            ImageAsset imageAsset = mCrate.assets.images.LIST.get(position % mActualSize);
            holder.imageView.setImageBitmap(mCrate.getBitmap(imageAsset));
        }

        @Override
        public int getItemCount() {
            return Integer.MAX_VALUE;
        }
    }

    private static final class SvgViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        SvgImageView imageView;

        SvgViewHolder(@NonNull View view) {
            super(view);
            imageView = (SvgImageView) view.findViewById(R.id.image_view);
        }
    }

    private static final class SvgRecyclerAdapter extends RecyclerView.Adapter<SvgViewHolder> {

        @NonNull
        private final Crate mCrate;

        private final int mActualSize;

        SvgRecyclerAdapter(@NonNull Crate crate) {
            mCrate = crate;
            mActualSize = mCrate.assets.svgs.LIST.size();
        }

        @Override
        public SvgViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.svg_view_item, viewGroup, false);
            return new SvgViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SvgViewHolder holder, int position) {
            SvgAsset svgAsset = mCrate.assets.svgs.LIST.get(position % mActualSize);
            holder.imageView.loadSvgBitmap(mCrate, svgAsset);
        }

        @Override
        public int getItemCount() {
            return Integer.MAX_VALUE;
        }
    }
}
