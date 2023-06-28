package id.co.mii.serverapp.controller;

import id.co.mii.serverapp.models.Region;
import id.co.mii.serverapp.services.RegionService;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // json
@RequestMapping("/region")
@AllArgsConstructor
@PreAuthorize("hasRole('USER')")
public class RegionController {

  private RegionService regionService;

  @GetMapping
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public List<Region> getAll() {
    return regionService.getAll();
  }

  @GetMapping("/{id}")
  public Region getById(@PathVariable Integer id) {
    return regionService.getById(id);
  }

  // http://localhost:9000/region/id => path
  // http://localhost:9000/region?id=1 => param

  @PostMapping
  public Region create(@RequestBody Region region) {
    return regionService.create(region);
  }

  @PutMapping("/{id}")
  public Region update(@PathVariable Integer id, @RequestBody Region region) {
    return regionService.update(id, region);
  }

  @DeleteMapping("/{id}")
  public Region delete(@PathVariable Integer id) {
    return regionService.delete(id);
  }

  // JPQL
  @GetMapping("/jpql")
  public List<Region> searchByName(@RequestParam(name = "name") String name) {
    return regionService.searchByName(name);
  }

  // Native
  @GetMapping("/native/{name}")
  public List<Region> searchByNameNative(@PathVariable String name) {
    return regionService.searchByNameNative(name);
  }
}