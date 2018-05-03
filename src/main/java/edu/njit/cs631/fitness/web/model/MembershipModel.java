package edu.njit.cs631.fitness.web.model;

import edu.njit.cs631.fitness.validation.ValidPrice;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MembershipModel {

    private static final Pattern PRICE_RE = Pattern.compile("^\\$?(\\d+\\.\\d{2})$");

    @NotNull
    @Size(min = 1, message = "Must provide a name for this membership.")
    private String membershipName;

    @NotNull
    @Size(min = 1, message = "Must provide a description for this membership.")
    private String membershipAdvantages;

    @NotNull
    @ValidPrice
    private String cost;

    public MembershipModel() {}

    public String getMembershipName() {
        return membershipName;
    }

    public void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }

    public String getMembershipAdvantages() {
        return membershipAdvantages;
    }

    public void setMembershipAdvantages(String membershipAdvantages) {
        this.membershipAdvantages = membershipAdvantages;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public BigDecimal getDecimalCost() {
        Matcher matcher = PRICE_RE.matcher(cost);

        if (matcher.matches()) {
            return new BigDecimal(matcher.group(1));
        }

        return null;
    }
}
