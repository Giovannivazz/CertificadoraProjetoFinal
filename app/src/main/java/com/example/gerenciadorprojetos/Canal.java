package com.example.gerenciadorprojetos;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;


@Entity(tableName = "canais")
public class Canal implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nome;
    public Canal(String nome) {
        this.nome = nome;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public String toString() {
        return this.nome;
    }
}