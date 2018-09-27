package com.capstone1.tutoryapi.model

import javax.persistence.*

/**
 * Created by Nguyen Van Phuc on 9/26/18
 */
@Entity
@Table(name = "employee")
data class Employer(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int? = 0,
        @Column(name = "name")
        var name: String? = "",
        @Column(name = "title")
        var title: String? = "")
