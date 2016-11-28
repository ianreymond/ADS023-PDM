package br.com.reymond.lawrence.oqrola.activity;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.List;

import br.com.reymond.lawrence.oqrola.R;
import br.com.reymond.lawrence.oqrola.fragments.PartyFragment;
import br.com.reymond.lawrence.oqrola.model.Party;


/**
 * Created by ian on 25-Nov-16.
 */

public class MainActivity extends ActionBarActivity {

    private static String TAG = "LOG";
    private static GoogleMap mMap;
    private static LocationManager locationManager;
    private Toolbar mToolbar;
    private Drawer.Result navigationDrawerLeft;
    private Drawer.Result navigationDrawerRight;
    private AccountHeader.Result headerNavigationLeft;
    private int mPositionClicked;
    /**
     * Opção de Notificações
    */
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Toast.makeText(MainActivity.this, "onCheckedChanged: "+( b ? "true" : "false" ), Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle("OQRola");
        mToolbar.setSubtitle("A sua Festa e Aqui!");
        mToolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(mToolbar);



        /**
         * Fragmento
          */
        PartyFragment frag = (PartyFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");
        if(frag == null) {
            frag = new PartyFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container, frag, "mainFrag");
            ft.commit();
        }
        // NAVIGATIOn DRAWER
        // END - RIGHT
        navigationDrawerRight = new Drawer()
                .withActivity(this)
                //.withToolbar(mToolbar)
                .addDrawerItems(
                        new SecondaryDrawerItem().withName("Quero Festa").withIcon(getResources().getDrawable(R.drawable.querofesta_inativo)),
                        new SecondaryDrawerItem().withName("Hora do Rango").withIcon(getResources().getDrawable(R.drawable.horadorango_inativo)),
                        new SecondaryDrawerItem().withName("Happy Hour").withIcon(getResources().getDrawable(R.drawable.happyhour_inativo)),
                        new SecondaryDrawerItem().withName("Time Cult").withIcon(getResources().getDrawable(R.drawable.timecult_inativo)),
                        new SecondaryDrawerItem().withName("Censura Livre").withIcon(getResources().getDrawable(R.drawable.censuralivre_inativo))
                )
                .withDisplayBelowToolbar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.END)
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(-1)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        Toast.makeText(MainActivity.this, "onItemClick: " + i, Toast.LENGTH_SHORT).show();
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        Toast.makeText(MainActivity.this, "onItemLongClick: " + i, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .build();

         headerNavigationLeft = new AccountHeader()
                .withActivity(this)
                .withCompactStyle(false)
                .withSavedInstance(savedInstanceState)
                .withThreeSmallProfileImages(true)
                .withHeaderBackground(R.drawable.profile_back)
                .addProfiles(
                        new ProfileDrawerItem().withName("Ian").withEmail("lawrence.reymond@gmail.com").withIcon(getResources().getDrawable(R.drawable.person_1))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile iProfile, boolean b) {
                        Toast.makeText(MainActivity.this, "onProfileChanged: " + iProfile.getName(), Toast.LENGTH_SHORT).show();
                        headerNavigationLeft.setBackgroundRes(R.drawable.profile_back2);
                        return false;
                    }
                })
                .build();

        navigationDrawerLeft = new Drawer()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withDisplayBelowToolbar(false)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(0)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerNavigationLeft)

                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        for (int count = 0, tam = navigationDrawerLeft.getDrawerItems().size(); count < tam; count++) {
                            if (count == mPositionClicked && mPositionClicked <= 4) {
                                PrimaryDrawerItem aux = (PrimaryDrawerItem) navigationDrawerLeft.getDrawerItems().get(count);
                                aux.setIcon(getResources().getDrawable( getCorrectDrawerIcon( count, false ) ));
                                break;
                            }
                        }

                        if(i <= 4){
                            ((PrimaryDrawerItem) iDrawerItem).setIcon(getResources().getDrawable( getCorrectDrawerIcon( i, true ) ));
                        }

                        mPositionClicked = i;
                        navigationDrawerLeft.getAdapter().notifyDataSetChanged();
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        Toast.makeText(MainActivity.this, "onItemLongClick: " + i, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .build();

        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Quero Festa").withIcon(getResources().getDrawable(R.drawable.querofesta_inativo)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Hora do Rango").withIcon(getResources().getDrawable(R.drawable.horadorango_inativo)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Happy Hour").withIcon(getResources().getDrawable(R.drawable.happyhour_inativo)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Time Cult").withIcon(getResources().getDrawable(R.drawable.timecult_inativo)));
        navigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Censura Livre").withIcon(getResources().getDrawable(R.drawable.censuralivre_inativo)));

        navigationDrawerLeft.addItem(new SectionDrawerItem().withName("Configurações"));


     //   navigationDrawerLeft.addItem(new SwitchDrawerItem().withName("Notificação").withChecked(true).withOnCheckedChangeListener((OnCheckedChangeListener) mOnCheckedChangeListener));
        navigationDrawerLeft.addItem( new SwitchDrawerItem().withName("Notificação").withChecked(true));
    }

    private int getCorrectDrawerIcon(int position, boolean isSelecetd){
        switch(position){
            case 0:
                return( isSelecetd ? R.drawable.querofesta_ativo : R.drawable.querofesta_inativo );
            case 1:
                return( isSelecetd ? R.drawable.horadorango_ativo : R.drawable.horadorango_inativo );
            case 2:
                return( isSelecetd ? R.drawable.happyhour_ativo : R.drawable.happyhour_inativo );
            case 3:
                return( isSelecetd ? R.drawable.timecult_ativo : R.drawable.timecult_inativo );
            case 4:
                return( isSelecetd ? R.drawable.censuralivre_ativo : R.drawable.censuralivre_inativo );
        }
        return(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_second_activity){
            startActivity(new Intent(this, LoginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public List<Party> getSetPartyList(int qtd){
        String[] nomes = new String[]{"Festa Surreal", "Dudu Bar", "Frans Cafe", "Brownie", "Na Praia", "Nicolandia", "Carreira Kart", "Mostra Cultural", "Cultura", "Resenha"};
        String[] produtoras = new String[]{"R2", "Bar", "Frans", "Rango", "R2", "Parque", "Kart", "Governo DF", "Cultura", "Bar"};
        int[] fotos = new int[]{R.drawable.surrealr2, R.drawable.bar_dudu, R.drawable.rango_frans, R.drawable.rango_brownie, R.drawable.napraia, R.drawable.livre_nico, R.drawable.livre_kart, R.drawable.cult_mostra, R.drawable.cul_cultura, R.drawable.bar_resenha};
        String[] locals = new String[]{"-15.699444,-47.8319107", "-15.8035946,-47.8923318", "-15.8035946,-47.8923318", "-15.8035946,-47.8923318", "-15.8035946,-47.8923318", "-15.8035946,-47.8923318", "-15.8035946,-47.8923318", "-15.8035946,-47.8923318", "-15.8035946,-47.8923318", "-15.8035946,-47.8923318"};
        List<Party> listAux = new ArrayList<>();

        for(int i = 0; i < qtd; i++){
            Party c = new Party( nomes[i % nomes.length], produtoras[ i % produtoras.length ], fotos[i % nomes.length], locals[ i % locals.length] );
            listAux.add(c);
        }
        return(listAux);
    }




}
