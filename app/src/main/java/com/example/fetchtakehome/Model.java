package com.example.fetchtakehome;

/**
 * Model class that represents a single item in the list, storing
 * the list ID, name, and ID for each item, used for the RecyclerView.
 */
public class Model {
    Integer listId;
    String name;
    Integer id;

    /**
     * Constructor for Model.
     * @param listId Item's list ID.
     * @param name Item's name.
     * @param id Item's ID.
     */
    public Model(Integer listId, String name, Integer id) {
        this.listId = listId;
        this.name = name;
        this.id = id;
    }

    /**
     * Gets the item's list ID.
     * @return Item's list ID.
     */
    public Integer getListId() {
        return listId;
    }

    /**
     * Sets the item's list ID.
     * @param listId The new list ID to set.
     */
    public void setListId(Integer listId) {
        this.listId = listId;
    }

    /**
     * Gets the item's name.
     * @return Item's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the item's name.
     * @param name The new name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the item's ID.
     * @return item's ID.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the item's ID.
     * @param id The new ID to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }
}
