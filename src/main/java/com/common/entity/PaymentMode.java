package com.common.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sce_pay_mode", schema = "sce_stud_payments")
public class PaymentMode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pay_mode_id")
	private int payment_mode_id;

	@Column(name = "pay_mode_name")
	private String payment_type;

	@Column(name = "is_active")
	private int is_active;

	@Column(name = "created_by")
	private int created_by;

	@Column(name = "updated_by")
	private Integer updated_by;

	@Column(name = "created_date")
	private LocalDateTime created_date;

	@Column(name = "updated_date")
	private LocalDateTime updated_date;
}
