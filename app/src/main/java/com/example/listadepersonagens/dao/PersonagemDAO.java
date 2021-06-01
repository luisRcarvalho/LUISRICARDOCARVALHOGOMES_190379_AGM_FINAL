package com.example.listadepersonagens.dao;

import android.app.Person;

import com.example.listadepersonagens.model.Personagem;

import java.util.ArrayList;
import java.util.List;

public class PersonagemDAO {
//classe onde se salva,edita e organiza os novos personagens inseridos

    private final static List<Personagem> personagens = new ArrayList<>();
    private static int contadorDeId = 1;
//metodo onde se adiciona e salva os peronsagens novos criados
    public void salva(Personagem personagemSalvo) {
        personagemSalvo.setId( contadorDeId );
        personagens.add( personagemSalvo );
        contadorDeId++;
    }
    private void atualizaId(){contadorDeId++;}
//metodo onde se faz a edição dos personagens ja criados
    public void edita(Personagem personagem) {
    Personagem personagemEscolhido = buscaPersonagemID( personagem );
    if(personagemEscolhido!=null){
        int posicaoDoPersonagem = personagens.indexOf(personagemEscolhido );
        personagens.set(posicaoDoPersonagem,personagem );
        }
    }
    private Personagem buscaPersonagemID(Personagem personagem){
        for(Personagem p:
        personagens){
            if(p.getId()== personagem.getId()){
                return p;
            }
        }
        return  null;
    }
    public List<Personagem>todos(){
        return  new ArrayList<>(personagens);
    }

    public void remove(Personagem personagem) {
        Personagem personagemDevolvido = buscaPersonagemID(personagem);
        if(personagemDevolvido != null){
            personagens.remove( personagemDevolvido);
        }
    }
}
