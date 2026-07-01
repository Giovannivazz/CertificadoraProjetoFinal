package com.example.gerenciadorprojetos;





import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import java.time.LocalDate;


@Entity(tableName = "videos",
        foreignKeys = @ForeignKey(entity = Canal.class,
                parentColumns = "id",
                childColumns = "canalId",
                onDelete = ForeignKey.CASCADE))
public class Video implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String titulo;
    private String formato;

    private int canalId;
    private LocalDate dataPublicacao;

    public Video(String titulo, String formato, int canalId, LocalDate dataPublicacao) {
        this.titulo = titulo;
        this.formato = formato;
        this.canalId = canalId;
        this.dataPublicacao = dataPublicacao;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getFormato() { return formato; }
    public void setFormato(String formato) { this.formato = formato; }

    public int getCanalId() { return canalId; }
    public void setCanalId(int canalId) { this.canalId = canalId; }

    public LocalDate getDataPublicacao() { return dataPublicacao; }
    public void setDataPublicacao(LocalDate dataPublicacao) { this.dataPublicacao = dataPublicacao; }
}