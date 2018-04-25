package edu.njit.cs631.fitness.data.entity;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name=Membership.TABLE_NAME)
public class Membership {

    public static final String TABLE_NAME = "MEMBERSHIP";

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name="MEMBERSHIP_ID", nullable=false)
    private Integer id;
    public Integer getId() {
        return id;
    }

    @Column(name="MEMBERSHIP_TYPE")
    private String membershipType;
    public String getMembershipType() {
        return membershipType;
    }
    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    @Column(name="FEE")
    private BigDecimal fee;
    public BigDecimal getFee() {
        return fee;
    }
    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }


    @Column(name="ADVANTAGES")
    private String advantages;
    public String getAdvantages() {
        return advantages;
    }
    public void setAdvantages(String advantages) {
        this.advantages = advantages;
    }

    public List<String> splitAdvantages() {
        return Arrays.asList(getAdvantages().split("\\\\n"));
    }

}
