package coursenet.com.project;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    //Menampilkan Hasil Scann
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult hasil = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (hasil != null){
            String isinya = hasil.getContents();
            Toast.makeText(getApplicationContext(), isinya, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //digunakan untuk menampilkan menu diaktivity ini
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //dijalankan ketika menunya diklik
        if (item.getItemId() == R.id.menuHome) {

        } else if (item.getItemId() == R.id.menuAbout) {

            Toast.makeText(getApplicationContext(), "CREATED BY RIDWAN", Toast.LENGTH_LONG).show();
            finish();

        } else if (item.getItemId() == R.id.menuLogout) {

            Intent b = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(b);
            finish();

        } else if (item.getItemId() == R.id.menuExit) {

            AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
            ab.setTitle(R.string.title_exit);
            ab.setMessage("are you sure??");
            ab.setIcon(R.drawable.ic_person_black_24dp);

            ab.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAffinity();
                }
            });
            ab.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            ab.create().show();

        } else if (item.getItemId()==R.id.menuChangepassword){

        }

        toggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer1);
        NavigationView nav1 = (NavigationView) findViewById(R.id.nav1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toggle = new ActionBarDrawerToggle(MainActivity.this, drawer1, (R.string.open), (R.string.close));

        drawer1.addDrawerListener(toggle);

        nav1.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        if (menuItem.getItemId()==R.id.menuHome){

                            getSupportFragmentManager().beginTransaction().replace(R.id.frame1, new HomeFragment())
                                    .addToBackStack(null)
                                    .commit();

                            Toast.makeText(getApplicationContext(),"You Already In Your Home Page!!", Toast.LENGTH_LONG).show();

                            drawer1.closeDrawers();

                        }else if (menuItem.getItemId()==R.id.menuAbout){
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame1, new AboutFragment())
                                    .addToBackStack(null)
                                    .commit();
                            Toast.makeText(getApplicationContext(), "created by ridwan", Toast.LENGTH_LONG).show();

                            drawer1.closeDrawers();

                        }else if (menuItem.getItemId()==R.id.menuLogout){

                            getSharedPreferences("DATA_LOGIN", 0).edit().clear().commit();

                            Intent bgh = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(bgh);
                            finish();

                        }else if (menuItem.getItemId()==R.id.menuExit){
                            AlertDialog.Builder bvc = new AlertDialog.Builder(MainActivity.this);

                            bvc.setMessage("are you sure??");

                            bvc.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finishAffinity();
                                }
                            });

                            bvc.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            bvc.create().show();

                            drawer1.closeDrawers();
                        } else if (menuItem.getItemId()==R.id.menuChangepassword){

                            getSupportFragmentManager().beginTransaction().replace(R.id.frame1, new PasswordFragment()).addToBackStack(null).commit();

                            drawer1.closeDrawers();

                        } else if (menuItem.getItemId()==R.id.MenuTab){

                            getSupportFragmentManager().beginTransaction().replace(R.id.frame1, new TabFragment()).addToBackStack(null).commit();

                            drawer1.closeDrawers();
                        }

                        else if (menuItem.getItemId()==R.id.MenuTable){

                            getSupportFragmentManager().beginTransaction().replace(R.id.frame1, new TableFragment()).addToBackStack(null).commit();

                            drawer1.closeDrawers();

                        }

                        else if (menuItem.getItemId()== R.id.MenuScanner){

                            //https://www.qr-code-generator.com/
                            new IntentIntegrator(MainActivity.this).initiateScan();

                        }

                        else if (menuItem.getItemId()==R.id.MenuGoogleMap){

                            getSupportFragmentManager().beginTransaction().replace(R.id.frame1, new GoogleMapFragment()).addToBackStack(null).commit();

                            drawer1.closeDrawers();
                        }

                        else if (menuItem.getItemId()==R.id.MenuProgresDialog)
                        {
                            ProgressDialog pd = new ProgressDialog(MainActivity.this);
                            pd.setTitle("Loading...");
                            pd.setMessage("Just Wait..");
                            pd.setIcon(R.drawable.ic_vpn_key_black_24dp);
                            pd.show();

                        }
                        else if(menuItem.getItemId()==R.id.MenuListRegister)
                        {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frame1, new coursenet.com.project.ListFragment()).addToBackStack(null).commit();

                            drawer1.closeDrawers();
                        }

                        return false;
                    }
                }
        );


        String bebas = getIntent().getStringExtra("data1");

       Toast.makeText(getApplicationContext(), "Welcome" + bebas, Toast.LENGTH_LONG).show();
    }

    ActionBarDrawerToggle toggle;

    boolean clicback = false;

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() >0)

        {
            getSupportFragmentManager().popBackStackImmediate();
            return;
        }

        if (clicback==true){
            super.onBackPressed();
            return;
        }

        clicback = true;

        Toast.makeText(getApplicationContext(), " press once again to exit", Toast.LENGTH_LONG).show();

        Handler bio = new Handler();
        bio.postDelayed(new Runnable() {
            @Override
            public void run() {
                clicback = false;
            }
        }, 2000);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        toggle.syncState();
    }
}
