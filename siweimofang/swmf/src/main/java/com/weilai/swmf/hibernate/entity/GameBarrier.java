package com.weilai.swmf.hibernate.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GAME_BARRIER")
public class GameBarrier {

  @Id
  @Column(name = "ID")
  @GeneratedValue
  private long   id;

  @Column(name = "GAME_ID")
  private long   game_id;

  @Column(name = "BARRIER_NO")
  private String barrier_no;

  @Column(name = "DESCRIPTIONS")
  private String descriptions;

  @Column(name = "T_VALUE")
  private double  t_value;

  @Column(name = "C_VALUE")
  private double  c_value;

  @Column(name = "P_VALUE")
  private double  p_value;

  @Column(name = "CREATE_AT")
  private Date   create_at;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getGame_id() {
    return game_id;
  }

  public void setGame_id(long game_id) {
    this.game_id = game_id;
  }

  public String getBarrier_no() {
    return barrier_no;
  }

  public void setBarrier_no(String barrier_no) {
    this.barrier_no = barrier_no;
  }

  public String getDescriptions() {
    return descriptions;
  }

  public void setDescriptions(String descriptions) {
    this.descriptions = descriptions;
  }

  public double getT_value() {
    return t_value;
  }

  public void setT_value(double t_value) {
    this.t_value = t_value;
  }

  public double getC_value() {
    return c_value;
  }

  public void setC_value(double c_value) {
    this.c_value = c_value;
  }

  public double getP_value() {
    return p_value;
  }

  public void setP_value(double p_value) {
    this.p_value = p_value;
  }

  public Date getCreate_at() {
    return create_at;
  }

  public void setCreate_at(Date create_at) {
    this.create_at = create_at;
  }

}
