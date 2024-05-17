package com.mounanga.enterprise.users.dto.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PageModel<T> {
    private int page;
    private int totalPages;
    private int size;
    private long totalElements;
    private int numberOfElements;
    private int number;
    private List<T> content;
    private boolean hasContent;
    private boolean hasNext;
    private boolean hasPrevious;
    private boolean isFirst;
    private boolean isLast;

    private PageModel(){
        super();
    }

    @Contract(value = " -> new", pure = true)
    public static <T> @NotNull Builder<T> builder() {
        return new Builder<>();
    }

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public int getNumber() {
        return number;
    }

    public List<T> getContent() {
        return content;
    }

    public boolean isHasContent() {
        return hasContent;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public boolean isLast() {
        return isLast;
    }

    public static class Builder<T> {
        private int page;
        private int totalPages;
        private int size;
        private long totalElements;
        private int numberOfElements;
        private int number;
        private List<T> content;
        private boolean hasContent;
        private boolean hasNext;
        private boolean hasPrevious;
        private boolean isFirst;
        private boolean isLast;

        private Builder() {
            // Private constructor to prevent external instantiation
        }

        public Builder<T> page(int page) {
            this.page = page;
            return this;
        }

        public Builder<T> totalPages(int totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public Builder<T> size(int size) {
            this.size = size;
            return this;
        }

        public Builder<T> totalElements(long totalElements) {
            this.totalElements = totalElements;
            return this;
        }

        public Builder<T> numberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
            return this;
        }

        public Builder<T> number(int number) {
            this.number = number;
            return this;
        }

        public Builder<T> content(List<T> content) {
            this.content = content;
            return this;
        }

        public Builder<T> hasContent(boolean hasContent) {
            this.hasContent = hasContent;
            return this;
        }

        public Builder<T> hasNext(boolean hasNext) {
            this.hasNext = hasNext;
            return this;
        }

        public Builder<T> hasPrevious(boolean hasPrevious) {
            this.hasPrevious = hasPrevious;
            return this;
        }

        public Builder<T> isFirst(boolean isFirst) {
            this.isFirst = isFirst;
            return this;
        }

        public Builder<T> isLast(boolean isLast) {
            this.isLast = isLast;
            return this;
        }

        public PageModel<T> build() {
            PageModel<T> pageModel = new PageModel<>();
            pageModel.page = this.page;
            pageModel.totalPages = this.totalPages;
            pageModel.size = this.size;
            pageModel.totalElements = this.totalElements;
            pageModel.numberOfElements = this.numberOfElements;
            pageModel.number = this.number;
            pageModel.content = this.content;
            pageModel.hasContent = this.hasContent;
            pageModel.hasNext = this.hasNext;
            pageModel.hasPrevious = this.hasPrevious;
            pageModel.isFirst = this.isFirst;
            pageModel.isLast = this.isLast;
            return pageModel;
        }
    }
}
