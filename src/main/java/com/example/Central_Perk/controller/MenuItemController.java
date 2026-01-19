package com.example.Central_Perk.controller;

import com.example.Central_Perk.entity.MenuItem;
import com.example.Central_Perk.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/menu")
public class MenuItemController {

    private final MenuItemRepository menuItemRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public MenuItemController(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @PutMapping("/{id}")
public ResponseEntity<MenuItem> updateMenuItem(
        @PathVariable Long id,
        @RequestBody MenuItem updatedItem) {

    return menuItemRepository.findById(id)
            .map(item -> {
                item.setName(updatedItem.getName());
                item.setPrice(updatedItem.getPrice());
                item.setCategory(updatedItem.getCategory());
                item.setImageUrl(updatedItem.getImageUrl());
                MenuItem saved = menuItemRepository.save(item);
                return ResponseEntity.ok(saved);
            })
            .orElse(ResponseEntity.notFound().build());
}


    @DeleteMapping("/{id}")
public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
    menuItemRepository.deleteById(id);
    return ResponseEntity.noContent().build();
}


    @PostMapping
    public ResponseEntity<MenuItem> addMenuItem(
            @RequestPart("menuItem") MenuItem menuItem,
            @RequestPart("image") MultipartFile file) throws IOException {

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Save file with unique name
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        menuItem.setImageUrl(fileName); // store only file name

        MenuItem savedItem = menuItemRepository.save(menuItem);

        return ResponseEntity.ok(savedItem);
    }

    @GetMapping
    public ResponseEntity<?> getAllItems() {
        return ResponseEntity.ok(menuItemRepository.findAll());
    }
}
