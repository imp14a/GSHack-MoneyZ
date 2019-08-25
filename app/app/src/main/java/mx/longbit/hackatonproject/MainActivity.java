package mx.longbit.hackatonproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.File;

import mx.longbit.hackatonproject.fragment.HomeFragment;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private String drawerTitle;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        drawerTitle = getResources().getString(R.string.principal);
        if (savedInstanceState == null) {
            selectItem(drawerTitle);
        }



    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        String title = menuItem.getTitle().toString();
                        selectItem(title);
                        return true;
                    }
                }
        );
    }

    private void selectItem(String title) {

        Bundle args = new Bundle();
        args.putString("args", title);
        androidx.fragment.app.Fragment fragment;

        switch (title) {
            case "Money Z":
                fragment = HomeFragment.newInstance(title);
                cambiaExternoFragment(fragment,title,args);
                break;
            case "Noticias":
                //fragment = NoticiasInstitucionalFragment.newInstance(title);
                //cambiaExternoFragment(fragment, title, args, internet, "");
                break;

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isDrawerOpen() { return drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START); }

    public void closeDrawer() {
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    public void onBackPressed(){
        if (isDrawerOpen()){
            closeDrawer();
        }else{
            FragmentManager fm = getSupportFragmentManager();
            Log.w("NUMERO", ""+fm.getBackStackEntryCount());
            switch (fm.getBackStackEntryCount()){

                case 1:
                    if (doubleBackToExitPressedOnce) {
                        super.onBackPressed();
                        finish();
                        return;
                    }

                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(this, "Vuelve a presionar para salir", Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce=false;
                        }
                    }, 2000);

                    break;
                case 2:
                    fm.popBackStack();
                    setTitle("UniMapp");
                    break;
                default:
                    fm.popBackStack();
                    break;
            }
        }
    }

    public void cambiaExternoFragment(Fragment fragment, String title, Bundle args){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragment.setArguments(args);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, fragment)
                    .addToBackStack(null)
                    .commit();
            drawerLayout.closeDrawers();
            setTitle(title);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("Execute onDestroy");
        try {
            File dir = this.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }
    public boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
}
