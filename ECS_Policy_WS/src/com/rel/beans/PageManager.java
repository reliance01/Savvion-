package com.rel.beans;

import java.util.ArrayList;
import java.util.List;

public class PageManager {
   private int startPage = 1;
   private int startIndex = 0;
   private int endPage = -1;
   private int endIndex = -1;
   private int rowsPerPage = -1;
   private int totalRecords = -1;
   private int totalPageNum = -1;
   private int currentPage = -1;

   public PageManager() {
      this.rowsPerPage = 15;
   }

   public PageManager(int rowsPerPage) {
      this.rowsPerPage = rowsPerPage;
   }

   public void setPaging(String currentPageStr, int noOfRecords, int rowsPerPage) {
      this.rowsPerPage = rowsPerPage;
      this.setPaging(currentPageStr, noOfRecords);
   }

   public void setPaging(String currentPageStr, int noOfRecords) {
      this.totalRecords = noOfRecords;
      this.totalPageNum = noOfRecords / this.rowsPerPage;
      if (noOfRecords % this.rowsPerPage != 0) {
         ++this.totalPageNum;
      }

      if (PageUtil.self().isNullorEmpty(currentPageStr)) {
         this.currentPage = 1;
      } else {
         try {
            for(this.currentPage = Integer.parseInt(currentPageStr); this.currentPage > 1 && this.totalRecords <= (this.currentPage - 1) * this.rowsPerPage; --this.currentPage) {
            }

            if (this.currentPage < 1) {
               this.currentPage = 1;
            }
         } catch (NumberFormatException var4) {
            this.currentPage = 1;
         }
      }

      this.startIndex = (this.currentPage - 1) * this.rowsPerPage;
      if (this.totalPageNum == 0) {
         this.endIndex = 0;
         this.endPage = 1;
      } else {
         this.endIndex = this.totalPageNum == this.currentPage && noOfRecords % this.rowsPerPage != 0 ? this.startIndex + noOfRecords % this.rowsPerPage - 1 : this.startIndex + this.rowsPerPage - 1;
         this.endPage = this.totalPageNum;
      }

   }

   public int getStartPage() {
      return this.startPage;
   }

   public int getEndPage() {
      return this.endPage;
   }

   public int getCurrentPage() {
      return this.currentPage;
   }

   public int getTotalRecords() {
      return this.totalRecords;
   }

   public int getTotalPage() {
      return this.totalPageNum;
   }

   public int getStartIndex() {
      return this.startIndex;
   }

   public int getEndIndex() {
      return this.endIndex;
   }

   public boolean isFirstPage() {
      return this.currentPage == 1;
   }

   public boolean isLastPage() {
      return this.currentPage == this.totalPageNum || this.totalPageNum == 0;
   }

   public List<Object> getSubList(List<Object> list) {
      List<Object> subList = new ArrayList();
      if (list != null) {
         int index = 0;
         if (this.getEndIndex() > 0) {
            index = this.getEndIndex() - (this.getStartIndex() - 1);
         } else {
            index = this.getEndIndex() - this.getStartIndex();
         }

         if (index > 0) {
            subList = list.subList(this.getStartIndex(), this.getEndIndex() + 1);
         } else {
            subList = list;
         }
      }

      return (List)subList;
   }
}
