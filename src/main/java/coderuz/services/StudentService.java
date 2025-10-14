package coderuz.services;

import coderuz.dto.PageResponse;
import coderuz.dto.StudentDTO;
import coderuz.entity.StudentEntity;
import coderuz.enums.Gender;
import coderuz.mapper.StudentInfoMapper;
import coderuz.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public StudentDTO create(StudentDTO studentDTO) {
        StudentEntity studentEntity = toEntity(studentDTO);

        studentRepository.save(studentEntity);
        studentDTO.setId(studentEntity.getId());
        studentDTO.setCreatedAt(studentEntity.getCreatedAt());

        return studentDTO;
    }

    public List<StudentDTO> findAll() {
        Iterable<StudentEntity> iterable = studentRepository.findAll();
        List<StudentDTO> list = new LinkedList<>();

        for (StudentEntity studentEntity : iterable) {

            list.add(toDTO(studentEntity));
        }
        return list;
    }

    public StudentDTO toDTO(StudentEntity studentEntity) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(studentEntity.getId());
        studentDTO.setName(studentEntity.getName());
        studentDTO.setSurname(studentEntity.getSurname());
        studentDTO.setAge(studentEntity.getAge());
        studentDTO.setGender(studentEntity.getGender());
        studentDTO.setLevel(studentEntity.getLevel());
        studentDTO.setCreatedAt(studentEntity.getCreatedAt());
        return studentDTO;
    }

    public StudentEntity toEntity(StudentDTO studentDTO) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setName(studentDTO.getName());
        studentEntity.setSurname(studentDTO.getSurname());
        studentEntity.setAge(studentDTO.getAge());
        studentEntity.setGender(studentDTO.getGender());
        studentEntity.setLevel(studentDTO.getLevel());
        return studentEntity;
    }

    public StudentDTO getById(Integer id) {
        Optional<StudentEntity> optional = studentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Student with id " + id + " not found");
        }
        return toDTO(optional.get());
    }

    @Transactional
    public StudentDTO updateById(Integer id, StudentDTO studentDTO) {
        Optional<StudentEntity> optional = studentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Student with id " + id + " not found");
        }
        StudentEntity entity = optional.get();
        entity.setName(studentDTO.getName());
        entity.setSurname(studentDTO.getSurname());
        entity.setAge(studentDTO.getAge());
        entity.setGender(studentDTO.getGender());
        entity.setLevel(studentDTO.getLevel());
        studentRepository.updateStudentById(studentDTO.getName(), studentDTO.getSurname(), studentDTO.getLevel(), studentDTO.getAge(), studentDTO.getGender(), id);

        studentDTO.setId(id);
        return studentDTO;

//        int result = studentRepository.updateNameAndSurname(studentDTO.getName(), studentDTO.getSurname(), id);
//        if(result == 0){
//            throw new IllegalArgumentException("Student with id " + id + " not found");
//        }
//        return result;

    }

    @Transactional
    public String deleteById(Integer id) {
        Optional<StudentEntity> optional = studentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Student with id " + id + " not found");
        }
        studentRepository.deleteById(id);
        return "Student with id " + id + " has been deleted";
    }

    public List<StudentDTO> findAllByName(String name) {
        Iterable<StudentEntity> iterable = studentRepository.findByName(name);
        List<StudentDTO> list = new LinkedList<>();

        if (!iterable.iterator().hasNext()) {
            throw new IllegalArgumentException("Student with name " + name + " not found");
        }

        for (StudentEntity studentEntity : iterable) {
            list.add(toDTO(studentEntity));
        }
        return list;
    }

    public List<StudentDTO> findAllBySurname(String surname) {
        Iterable<StudentEntity> iterable = studentRepository.findBySurname(surname);
        List<StudentDTO> list = new LinkedList<>();

        if (!iterable.iterator().hasNext()) {
            throw new IllegalArgumentException("Student with surname " + surname + " not found");
        }

        for (StudentEntity studentEntity : iterable) {
            list.add(toDTO(studentEntity));
        }
        return list;
    }

    public List<StudentDTO> findAllByLevel(Integer level) {
        Iterable<StudentEntity> iterable = studentRepository.findByLevel(level);
        List<StudentDTO> list = new LinkedList<>();
        if (!iterable.iterator().hasNext()) {
            throw new IllegalArgumentException("Student with level " + level + " not found");
        }
        for (StudentEntity entity : iterable) {
            list.add(toDTO(entity));
        }

        return list;
    }

    public List<StudentDTO> findAllByAge(Integer age) {
        Iterable<StudentEntity> iterable = studentRepository.findByAge(age);
        List<StudentDTO> list = new LinkedList<>();
        if (!iterable.iterator().hasNext()) {
            throw new IllegalArgumentException("Student with age " + age + " not found");
        }
        for (StudentEntity entity : iterable) {
            list.add(toDTO(entity));
        }

        return list;
    }

    public List<StudentDTO> findAllByGender(Gender gender) {
        Iterable<StudentEntity> iterable = studentRepository.findAllByGender(gender);
        List<StudentDTO> list = new LinkedList<>();
        if (!iterable.iterator().hasNext()) {
            throw new IllegalArgumentException("Student with gender " + gender + " not found");
        }

        for (StudentEntity entity : iterable) {
            list.add(toDTO(entity));
        }

        return list;
    }

    public List<StudentDTO> findAllByCreatedAtDate(LocalDate date) {
        LocalDateTime startDate = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endDate = LocalDateTime.of(date, LocalTime.MAX);

        List<StudentEntity> iterable = studentRepository.findByDate(startDate, endDate);
        List<StudentDTO> list = new LinkedList<>();
        if (iterable.isEmpty()) {
            throw new IllegalArgumentException("Student with this " + date + "date not found");
        }

        for (StudentEntity entity : iterable) {
            list.add(toDTO(entity));
        }

        return list;
    }

    public List<StudentDTO> findAllByCreatedAtBetween(LocalDate start, LocalDate end) {
        LocalDateTime startDate = LocalDateTime.of(start, LocalTime.MIN);
        LocalDateTime endDate = LocalDateTime.of(end, LocalTime.MAX);

        Iterable<StudentEntity> iterable = studentRepository.findByCreatedAtBetween(startDate, endDate);
        List<StudentDTO> list = new LinkedList<>();
        if (!iterable.iterator().hasNext()) {
            throw new IllegalArgumentException("Student with createdAt between " + start + " and " + end + " not found");
        }

        for (StudentEntity entity : iterable) {
            list.add(toDTO(entity));
        }

        return list;
    }

    public List<StudentDTO> findByAllByDetailPositionalNative(StudentDTO studentDTO) {
        List<StudentEntity> list = studentRepository.findByAllByDetailPositionalNative(studentDTO.getName(), studentDTO.getSurname(), studentDTO.getAge());
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Student with name " + studentDTO.getName() + " and surname " + studentDTO.getSurname() + " not found");
        }
        List<StudentDTO> listDTO = new LinkedList<>();
        for (StudentEntity entity : list) {
            listDTO.add(toDTO(entity));
        }
        return listDTO;
    }

    public void deleteByNameAndSurname(StudentDTO studentDTO) {
        studentRepository.deleteByNameAndSurname(studentDTO.getName(), studentDTO.getSurname());
    }

    public List<StudentDTO> getAllNameList() {
        List<Object[]> result = studentRepository.getAllNameList();

        List<StudentDTO> list = new LinkedList<>();

        for (Object[] object : result) {
            StudentDTO dto = new StudentDTO();
            dto.setName((String) object[0]);
            dto.setSurname((String) object[1]);

            list.add(dto);
        }
        return list;
    }

    public List<StudentInfoMapper> getShortInfo() {
        return studentRepository.getStudentInfo();
    }


    //=========Pagination==========//

    public PageImpl<StudentDTO> pagination(int page, int size) {
//        page = page - 1; //select * from student offset(page-1)*size limit size -> hibernate automatic (page - 1) qilmaydi yani page 1 dan emas 0 dan boshlanadi.
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<StudentEntity> pageObj = studentRepository.findAll(pageable); //StudentRepositoryda PagingAndSortingRepository dan extends qilganimiz uchun .findAll() pageable ni argument sifatida olyabdi.

        List<StudentEntity> entityList = pageObj.getContent();
        List<StudentDTO> dtoList = new LinkedList<>();
        for(StudentEntity entity : entityList){
            dtoList.add(toDTO(entity));
        }
        long total = pageObj.getTotalElements();

//        PageResponse<StudentDTO> pageResponse = new PageResponse<>();
//        pageResponse.setContent(dtoList);
//        pageResponse.setTotalCount(total);
        return new PageImpl<StudentDTO>(dtoList, pageable, total);
    }

    public PageImpl<StudentDTO> findByNameWithPagination(String name, int page, int size) {
        System.out.println("name>>>>" + name);
        System.out.println("page>>>>" + page);
        System.out.println("size>>>>" + size);
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<StudentEntity> result = studentRepository.findByName(name, pageable);

        long totalCount = result.getTotalElements();
        List<StudentEntity> entityList = result.getContent();

        List<StudentDTO> dtoList = new LinkedList<>();
        for(StudentEntity entity : entityList){
            System.out.println("name Entity>>>>" + entity.getName());
            dtoList.add(toDTO(entity));
        }

        return  new PageImpl<StudentDTO>(dtoList, pageable, totalCount);

    }
}
