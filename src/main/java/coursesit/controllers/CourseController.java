package coursesit.controllers;

import coursesit.Repositories.CourseRepository;
import coursesit.entities.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/homepage")
    public String showHomepage(Model model, @RequestParam(defaultValue = "0") int page) {
        // Создаем Pageable для пагинации
        Pageable pageable = PageRequest.of(page, 5);  // 5 курсов на странице
        Page<Course> coursePage = courseRepository.findAll(pageable);

        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", coursePage.getTotalPages());

        return "homepage";
    }

    @GetMapping("/add-course")
    public String showAddCourseForm(Model model) {
        model.addAttribute("course", new Course());
        return "add_course";
    }

    @PostMapping("/add-course")
    public String addCourse(@ModelAttribute("course") Course course) {
        System.out.println("Course title: " + course.getTitle());
        System.out.println("Course description: " + course.getDescription());
        courseRepository.save(course);
        return "redirect:/homepage";
    }


}

