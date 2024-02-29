package com.melita.orderapproval.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
	private Customer customerDetails;
	private String installationAddress;
	private LocalDateTime installationDateTime;
	@OneToMany(targetEntity = Product.class, cascade = { CascadeType.ALL })
	private List<Product> products;
	@CreationTimestamp
	private LocalDateTime createdDateTime;
	private boolean isApproved;

}
