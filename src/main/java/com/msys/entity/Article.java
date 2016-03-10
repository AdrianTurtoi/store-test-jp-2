package com.msys.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ARTICLE")
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@NotNull
	@Column(name = "ARTICLE_NO")
	private int articleNo;
	
	@Column(name = "ARTICLE_NAME")
	private String articleName;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "articles", cascade = CascadeType.ALL)
	Set<OrderItem> orderItem;

	public Article(int articleNo, String articleName) {
		super();
		this.articleNo = articleNo;
		this.articleName = articleName;
	}	

	public Article(int articleNo) {
		this.articleNo = articleNo;
		this.articleName = "NoNameArticle";
	}
	
	public Article(Set<OrderItem> orderItem, int articleNo, String articleName) {
		super();
		this.orderItem = orderItem;	
		this.articleNo = articleNo;
		this.articleName = articleName;
	}
	
	public Article () {		
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Article(int articleNo, String articleName, Long id) {
		super();
		this.articleNo = articleNo;
		this.articleName = articleName;
		this.id = id;
	}

	public Set<OrderItem> getOrderItem() {
		return orderItem;
	} 

	public void setOrderItem(Set<OrderItem> orderItem) {
		this.orderItem = orderItem;
	}

	public int getArticleNo() {
		return articleNo;
	}

	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	@Override
	public String toString() {
		return "Article [articleNo=" + articleNo + ", articleName=" + articleName + ", id=" + id + "]";
	}
}
