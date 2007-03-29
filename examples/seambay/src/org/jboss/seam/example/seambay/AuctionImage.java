package org.jboss.seam.example.seambay;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class AuctionImage implements Serializable
{
   private static final long serialVersionUID = -1219357931402690891L;

   private Integer imageId;
   private Auction auction;
   private byte[] data;
   
   @Id @GeneratedValue
   public Integer getImageId()
   {
      return imageId;
   }
   
   public void setImageId(Integer imageId)
   {
      this.imageId = imageId;
   }
   
   @ManyToOne
   @JoinColumn(name = "AUCTION_ID")
   public Auction getAuction()
   {
      return auction;
   }
   
   public void setAuction(Auction auction)
   {
      this.auction = auction;
   }
   
   @Lob
   public byte[] getData()
   {
      return data;
   }
   
   public void setData(byte[] data)
   {
      this.data = data;
   }
}
