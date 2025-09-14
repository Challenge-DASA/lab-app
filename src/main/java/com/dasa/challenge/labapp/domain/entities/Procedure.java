package com.dasa.challenge.labapp.domain.entities;

import java.util.List;

public class Procedure {
    private final String id;
    private final String name;
    private final String description;
    private final List<ProcedureItem> items;
    private boolean selected;

    public Procedure(String id, String name, String description, List<ProcedureItem> items) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.items = items;
        this.selected = false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<ProcedureItem> getItems() {
        return items;
    }

    public boolean isSelected() {
        return selected;
    }

    public void select() {
        this.selected = true;
    }

    public void deselect() {
        this.selected = false;
    }

    public void toggleSelection() {
        this.selected = !this.selected;
    }

    public String getDisplayName(int maxLength) {
        return name.length() > maxLength ?
                name.substring(0, maxLength - 3) + "..." : name;
    }

    public String getDisplayDescription(int maxLength) {
        return description.length() > maxLength ?
                description.substring(0, maxLength - 3) + "..." : description;
    }

    public String getFormattedItems(int maxLength) {
        String formattedItems = items.stream()
                .map(item -> String.format("%sx%s", item.getName(), item.getQuantity()))
                .collect(java.util.stream.Collectors.joining(", "));
        return formattedItems.length() > maxLength ? formattedItems.substring(0, maxLength - 3) + "..." : formattedItems;
    }
}