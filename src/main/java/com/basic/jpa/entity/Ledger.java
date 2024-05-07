package com.basic.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "LEDGER")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ledger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private Member member;

    @Column(nullable = false, length = 4, name = "ledger_category")
    private String category; // 수입, 지출, 저축

    @Column(nullable = false, name = "ledger_amount")
    private long amount;

    @Column(nullable = false, name = "ledger_detail")
    private String detail;

    @Column(nullable = false, name = "ledger_scheme")
    private String scheme;

    @CreatedDate
    @Column(nullable = false, name = "create_datetime")
    private LocalDateTime createDatetime;

    @LastModifiedDate
    @Column(nullable = false, name = "modify_datetime")
    private LocalDateTime modifyDatetime;
}