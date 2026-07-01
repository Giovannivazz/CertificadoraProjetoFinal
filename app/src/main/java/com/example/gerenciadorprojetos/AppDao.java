package com.example.gerenciadorprojetos;

import androidx.room.*;
import java.util.List;


@Dao
public interface AppDao {

    //canais
    @Insert void inserirCanal(Canal canal);
    @Update void atualizarCanal(Canal canal);
    @Delete void excluirCanal(Canal canal);
    @Query("SELECT * FROM canais") List<Canal> getAllCanais();

    //videos
    @Insert void inserirVideo(Video video);
    @Update void atualizarVideo(Video video);
    @Delete void excluirVideo(Video video);
    @Query("SELECT * FROM videos") List<Video> getAllVideos();
}