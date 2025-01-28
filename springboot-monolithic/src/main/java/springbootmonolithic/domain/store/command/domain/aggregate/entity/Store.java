package springbootmonolithic.domain.store.command.domain.aggregate.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "store")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code")
    private int code;

    @Column(
            name = "active",
            nullable = false,
            unique = false
    )
    private boolean active;

    @Column(
            name = "created_at",
            nullable = false,
            unique = false
    )
    private String createdAt;

    @Column(
            name = "updated_at",
            nullable = false,
            unique = false
    )
    private String updatedAt;

    @Column(
            name = "brand",
            nullable = true,
            unique = false
    )
    private String brand;

    @Column(
            name = "name",
            nullable = false,
            unique = false
    )
    private String name;

    @Column(
            name = "address",
            nullable = false,
            unique = false
    )
    private String address;

    @Column(
            name = "address_detail",
            nullable = false,
            unique = false
    )
    private String addressDetail;

    @Column(
            name = "area",
            nullable = true,
            unique = false
    )
    private String area;

    @Column(
            name = "location",
            nullable = true,
            unique = false
    )
    private String location;

    @Column(
            name = "page_url",
            nullable = false,
            unique = false
    )
    private String pageUrl;

    @Column(
            name = "tel",
            nullable = true,
            unique = false
    )
    private String tel;

    @Column(
            name = "image",
            nullable = false,
            unique = false
    )
    private String image;
}
