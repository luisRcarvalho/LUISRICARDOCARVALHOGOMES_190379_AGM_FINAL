package com.example.listadepersonagens.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listadepersonagens.R;
import com.example.listadepersonagens.dao.PersonagemDAO;
import com.example.listadepersonagens.model.Personagem;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import static com.example.listadepersonagens.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;

public class FormularioPersonagemActivity extends AppCompatActivity {

    //variaveis dos nomes das paginas
    private static final String TITULO_APPBAR_EDITA_PERSONAGEM = "Editar Contato" ;
    private static final String TITULO_APPBAR_NOVO_PERSONAGEM = "Novo Contato";

    //variaveis dos campos preenchiveis do app
    private EditText campoNome;
    private EditText campoAltura;
    private EditText campoNascimento;
    private EditText campoTelefone;
    private EditText campoEndereco;
    private EditText campoCEP;
    private EditText campoRG;
    private EditText campoGenero;

    //varivel de criação de refenrecia ao personagem e personagemDao(Save)
    private final PersonagemDAO dao = new PersonagemDAO();

    private Personagem personagem;

    //criação do menu no formulario
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.activity_formulario_personagem_menu_salvar,menu);
        return super.onCreateOptionsMenu( menu );
    }
    //metodo que permite o clique
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_formulario_personagem_menu_salvar){
            finalizaFormulario();
        }
        return super.onOptionsItemSelected( item );
    }

    //quando inicializa o app, faz os ajustes de layout e inicializa os metodos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_formulario_personagem );
        inicializaCampos();
        configuraBotaoSalvar();
        carregaPersonagem();
    }
    //metodo que carrega os personagens ja criados
    private void carregaPersonagem() {
        Intent dados = getIntent();
        if (dados.hasExtra( CHAVE_PERSONAGEM )) {
            setTitle( TITULO_APPBAR_EDITA_PERSONAGEM );
            personagem = (Personagem) dados.getSerializableExtra( CHAVE_PERSONAGEM );
            preencheCampos();
        } else {
            setTitle( TITULO_APPBAR_NOVO_PERSONAGEM );
            personagem = new Personagem();
        }
    }
    //metodo que permite que os campos sejam preenchidos
    private void preencheCampos() {
        campoNome.setText( personagem.getNome() );
        campoAltura.setText( personagem.getAltura() );
        campoNascimento.setText( personagem.getNascimento() );
        campoTelefone.setText(personagem.getTelefone());
        campoEndereco.setText(personagem.getEndereco());
        campoCEP.setText(personagem.getCEP());
        campoRG.setText(personagem.getRG());
        campoGenero.setText(personagem.getGenero());
    }

    //metodo que realiza o save das informações
    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById( R.id.button_salvar );
        botaoSalvar.setOnClickListener( new View.OnClickListener() {
            @Override
            //chama o metodo de finalização ao apertar o botão
            public void onClick(View view) {
                finalizaFormulario();
            }
        });
    }

    //metodo onde se finaliza o preenchimento do formulario
    private void finalizaFormulario() {
        preenchePersonagem();
        //caso o personagem ja exista, utiliza o metodo de edição criado no dao
        if (personagem.IdValido()) {
            dao.edita( personagem );
            //e se nao existe, utiliza o metodo de salvar criado no dao para fazer um novo personagem
        } else {
            dao.salva( personagem );
        }
        finish();
    }
    //metodo que referencia as variveis dos campos, ao campos criados no layout
    private void inicializaCampos() {
        campoNome = findViewById( R.id.editText_nome );
        campoAltura = findViewById( R.id.editText_altura );
        campoNascimento = findViewById( R.id.editText_nascimento );
        campoTelefone = findViewById(R.id.editText_telefone);
        campoEndereco = findViewById(R.id.editText_endereco);
        campoRG = findViewById(R.id.editText_rg);
        campoCEP = findViewById(R.id.editText_cep);
        campoGenero = findViewById(R.id.editText_genero);

        //cria a mascara no campo da altura, que faz o digito ser separado por virgula apos o primeiro digito
        SimpleMaskFormatter smfAltura = new SimpleMaskFormatter("N,NN");
        MaskTextWatcher mtwAltura = new MaskTextWatcher( campoAltura, smfAltura );
        campoAltura.addTextChangedListener( mtwAltura );

        //cria a mascara no campo de nascimento , que faz o digito ser separado por dia/mes/ano
        SimpleMaskFormatter smfNascimento = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtwNascimento = new MaskTextWatcher( campoNascimento, smfNascimento );
        campoNascimento.addTextChangedListener( mtwNascimento );

        //cria a mascara no campo de Telefone , que faz o digito ser separado por (DDD) 99999-9999
        SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher mtwTelefone = new MaskTextWatcher( campoTelefone, smfTelefone );
        campoTelefone.addTextChangedListener( mtwTelefone);

        //cria a mascara no campo de RG , que faz o digito ser separado por XX.XXX.XXX-X
        SimpleMaskFormatter smfRG = new SimpleMaskFormatter("NN.NNN.NNN-N");
        MaskTextWatcher mtwRG = new MaskTextWatcher( campoRG, smfRG);
        campoRG.addTextChangedListener( mtwRG );

        //cria a mascara no campo de CEP , que faz o digito ser separado por XXXXX-XXX
        SimpleMaskFormatter smfCEP = new SimpleMaskFormatter("NNNNN-NNN");
        MaskTextWatcher mtwCEP = new MaskTextWatcher( campoCEP, smfCEP);
        campoCEP.addTextChangedListener( mtwCEP );

    }
    //metodo que preenche as variaveis com as informações escritas nos campos
    private void preenchePersonagem() {
        //conversão dos textos escritos no campo para string
        String nome = campoNome.getText().toString();
        String altura = campoAltura.getText().toString();
        String nascimento = campoNascimento.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String endereco = campoEndereco.getText().toString();
        String RG = campoRG.getText().toString();
        String CEP = campoCEP.getText().toString();
        String genero = campoGenero.getText().toString();

        //seta as informações
        personagem.setNome( nome );
        personagem.setAltura( altura );
        personagem.setNascimento( nascimento );
        personagem.setTelefone(telefone);
        personagem.setEndereco(endereco);
        personagem.setRG(RG);
        personagem.setCEP(CEP);
        personagem.setGenero(genero);
    }
}