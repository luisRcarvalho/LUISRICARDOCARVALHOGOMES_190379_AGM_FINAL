package com.example.listadepersonagens.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listadepersonagens.R;
import com.example.listadepersonagens.dao.PersonagemDAO;
import com.example.listadepersonagens.model.Personagem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.listadepersonagens.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;

public class ListaPersonagemActivity extends AppCompatActivity {
    //variavel com o nome da pagina
    public static final String TITULO_APPBAR = "Contatos";
    private final PersonagemDAO dao = new PersonagemDAO();
    private ArrayAdapter<Personagem> adapter;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_lista_de_personagem );
        setTitle( TITULO_APPBAR );
        configuraFabNovoPersonagem();
        configuraLista();
    }

    private void configuraFabNovoPersonagem() {
        //referenciando o floating action button colocado no layout
        FloatingActionButton botaoNovoPersonagem = findViewById( R.id.floatingActionButton );
        //ao clicar abre a pagina do formulario
        botaoNovoPersonagem.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abreFormularioSalva();
            }
        } );
    }

    private void abreFormularioSalva() {
        startActivity( new Intent( ListaPersonagemActivity.this, FormularioPersonagemActivity.class ) );
        atualizaPersonagem();
    }

    //proteção dos dados para que nao sumam ao dar back
    @Override
    protected void onResume() {
        super.onResume();
        atualizaPersonagem();
    }

    private void atualizaPersonagem() {
        adapter.clear();
        adapter.addAll( dao.todos() );
    }

    private void remove(Personagem personagem) {
        dao.remove( personagem );
        adapter.remove( personagem );
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu( menu, v, menuInfo );
       // menu.add( "Remover" );
        //menu.add( "Teste" );
        getMenuInflater().inflate(R.menu.activity_lista_personagem_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        configuraMenu( item );
        return super.onContextItemSelected( item );
    }
    //metodo para que abra a alertdialog para confirmar ou não a exclusão de um item
    private void configuraMenu(@NonNull MenuItem item) {
        int itemId =item.getItemId();
        if(itemId == R.id.activity_lista_personagem_menu_remover)
        {
            new AlertDialog.Builder(this)
                    .setTitle("Removendo Personagem")
                    .setMessage( "Tem certeza que deseja remover?" )
                    .setPositiveButton( "Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                            Personagem personagemEscolhido = adapter.getItem( menuInfo.position );
                            remove( personagemEscolhido );
                        }
                    } )
                    .setNegativeButton( "Não",null )
                    .show();
        }
    }

    //metodo que faz a configuração da lista, pegandos os personagens e possibilitando a configuração ao clicar
    private void configuraLista() {
        ListView listadePersonagens = findViewById( R.id.activity_main_lista_personagem );
        configuraAdapter( listadePersonagens );
        configuraItemPorClique( listadePersonagens );
        registerForContextMenu( listadePersonagens );
    }

    //metodo que permite ao clicar no personagem, a edição do mesmo
    private void configuraItemPorClique(ListView listadePersonagens) {
        listadePersonagens.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            //abre a janela de edição do personagem utilizando o metodo de edição
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Personagem personagemEscolhido = (Personagem) adapterView.getItemAtPosition( posicao );
                abreFormularioModoEditar( personagemEscolhido );
            }
        } );
    }

    //metodo de edição que vai abrir a janela editar
    private void abreFormularioModoEditar(Personagem personagem) {
        Intent vaiParaFormulario = new Intent( ListaPersonagemActivity.this, FormularioPersonagemActivity.class );
        vaiParaFormulario.putExtra( CHAVE_PERSONAGEM, personagem );
        startActivity( vaiParaFormulario );
    }

    //metodo onde ficam salvos os personagem em layout de lista
    private void configuraAdapter(ListView listadePersonagens) {
        adapter = new ArrayAdapter<>( this, android.R.layout.simple_list_item_1 );
        listadePersonagens.setAdapter( adapter );
    }
}

