package com.lostfound.lostfound.service;

public @Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepo;
    private final UserRepository userRepo;
    private final CategoryRepository categoryRepo;
    private final LocationRepository locationRepo;

    public ItemResponse create(ItemRequest req) {
        Item item = new Item();
        item.setName(req.getName());
        item.setDescription(req.getDescription());
        item.setPhotoUrl(req.getPhotoUrl());
        item.setStatus(false);

        // link user
        if (req.getUserId() != null) {
            User user = userRepo.findById(req.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            item.setUser(user);
        }

        // link category
        if (req.getCategoryId() != null) {
            Category cat = categoryRepo.findById(req.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            item.setCategory(cat);
        }

        // link location
        if (req.getLocationId() != null) {
            Location loc = locationRepo.findById(req.getLocationId())
                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));
            item.setLocation(loc);
        }

        Item saved = itemRepo.save(item);
        return toResponse(saved);
    }

    private ItemResponse toResponse(Item it) {
        ItemResponse r = new ItemResponse();
        r.setId(it.getId());
        r.setName(it.getName());
        r.setDescription(it.getDescription());
        r.setPhotoUrl(it.getPhotoUrl());
        r.setStatus(it.isStatus());
        r.setUserId(it.getUser() != null ? it.getUser().getId() : null);
        r.setCategoryId(it.getCategory() != null ? it.getCategory().getId() : null);
        r.setLocationId(it.getLocation() != null ? it.getLocation().getId() : null);
        return r;
    }
}