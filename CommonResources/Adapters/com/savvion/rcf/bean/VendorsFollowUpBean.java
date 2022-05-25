
package com.savvion.rcf.bean;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class VendorsFollowUpBean
{
    private String loanNumber;

    private String docType;

    private String supplierName;

    private String customerName;

    private String address;

    private String phone;

    private String assetType;

    public String getAddress()
    {
        return address;
    }

    public void setAddress( String address )
    {
        this.address = address;
    }

    public String getAssetType()
    {
        return assetType;
    }

    public void setAssetType( String assetType )
    {
        this.assetType = assetType;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName( String customerName )
    {
        this.customerName = customerName;
    }

    public String getDocType()
    {
        return docType;
    }

    public void setDocType( String docType )
    {
        this.docType = docType;
    }

    public String getLoanNumber()
    {
        return loanNumber;
    }

    public void setLoanNumber( String loanNumber )
    {
        this.loanNumber = loanNumber;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone( String phone )
    {
        this.phone = phone;
    }

    public String getSupplierName()
    {
        return supplierName;
    }

    public void setSupplierName( String supplierName )
    {
        this.supplierName = supplierName;
    }

    public String toString()
    {
        return ToStringBuilder.reflectionToString( this,
                ToStringStyle.MULTI_LINE_STYLE );
    }
}
