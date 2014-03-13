package com.weilai.swmf.hibernate.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GAME")
public class Game {

  @Id
  @Column(name = "ID")
  @GeneratedValue
  private long id;
  
  @Column(name = "GAME")
  private String game;
  
  @Column(name = "GAME_NO")
  private String game_no;
  
  @Column(name = "FIRST_CATEGORY")
  private String first_category;
  
  @Column(name = "SECOND_CATEGORY")
  private String second_category;
  
  @Column(name = "FILE_NAME")
  private String file_name;
  
  @Column(name = "GAME_TOTAL_NUM")
  private int game_total_num;
  
  @Column(name = "IS_ONLINE")
  private boolean is_online;
  
  @Column(name = "PLATFORM")
  private String platform;
  
  @Column(name = "CREATE_AT")
  private Date create_at;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getGame() {
    return game;
  }

  public void setGame(String game) {
    this.game = game;
  }

  public String getGame_no() {
    return game_no;
  }

  public void setGame_no(String game_no) {
    this.game_no = game_no;
  }

  public String getFirst_category() {
    return first_category;
  }

  public void setFirst_category(String first_category) {
    this.first_category = first_category;
  }

  public String getSecond_category() {
    return second_category;
  }

  public void setSecond_category(String second_category) {
    this.second_category = second_category;
  }

  public String getFile_name() {
    return file_name;
  }

  public void setFile_name(String file_name) {
    this.file_name = file_name;
  }

  public int getGame_total_num() {
    return game_total_num;
  }

  public void setGame_total_num(int game_total_num) {
    this.game_total_num = game_total_num;
  }

  public boolean isIs_online() {
    return is_online;
  }

  public void setIs_online(boolean is_online) {
    this.is_online = is_online;
  }

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

  public Date getCreate_at() {
    return create_at;
  }

  public void setCreate_at(Date create_at) {
    this.create_at = create_at;
  }
  
}
