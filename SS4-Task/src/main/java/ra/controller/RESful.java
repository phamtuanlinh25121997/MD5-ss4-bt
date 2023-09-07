package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.Category;
import ra.model.entity.Post;
import ra.model.repository.ICategoryRepository;
import ra.model.repository.IPostRepository;
import ra.model.service.category.ICategoryService;
import ra.model.service.post.IPostService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RESful {
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IPostService postService;
    @Autowired
    private IPostRepository categoryRepository;
    @GetMapping
    public ResponseEntity<Iterable<Category>> categories() {
        List<Category> categories = (List<Category>) categoryService.findAll();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable Long id) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        if (!categoryOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(categoryOptional.get(), HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        return new ResponseEntity<>(categoryService.save(category), HttpStatus.CREATED);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        if (!categoryOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        category.setId(categoryOptional.get().getId());
        return new ResponseEntity<>(categoryService.save(category), HttpStatus.OK);
    }
    // Post
    @GetMapping("/post")
    public ResponseEntity<Iterable<Post>> post() {
        List<Post> posts = (List<Post>) postService.findAll();
        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Post> findPostById(@PathVariable Long id) {
        Optional<Post> postOptional = postService.findById(id);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(postOptional.get(), HttpStatus.OK);
        }
    }
    @GetMapping("/postbycate/{id}")
    public ResponseEntity<Iterable<Post>> postbycate(@PathVariable Long id) {
        Category category = new Category();
        category.setId(id);

        List<Post> posts =  categoryRepository.findPostByCategory(category);
        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
     }

    @PostMapping("/post")
    public ResponseEntity<Post> save(@RequestBody Post post) {
        return new ResponseEntity<>(postService.save(post), HttpStatus.CREATED);
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        Optional<Post> postOptional = postService.findById(id);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        post.setId(postOptional.get().getId());
        return new ResponseEntity<>(postService.save(post), HttpStatus.OK);
    }
}