package com.example.GVOne_blood.repository;

import com.example.GVOne_blood.dto.response.PageResponse;
import com.example.GVOne_blood.dto.response.ResponseUserDetail;
import com.example.GVOne_blood.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class SearchRepository {
    // tìm kiếm theo field, quẻry theo search
    @PersistenceContext
    public EntityManager entityManager;

    public PageResponse<?> getAllUsersWithSortColumnAndSearch(int pageNo, int pageSize, String sortBy, String search){
//        StringBuilder sql = new StringBuilder("SELECT u from User u where 1 = 1");

        // để tránh việc kết quả tạo ra lặp vô tận do Address có QH 1-n vs user, ta lấy ra 1 đối tượng
        // UserDetail để fix, ngoài ra cũng có thể dùng builder để map với user
        // muốn lấy ra bao nhiêu field thì cần 1 constructor có bấy nhiêu tham số
        StringBuilder sql = new StringBuilder("SELECT new com.example.GVOne_blood.dto.response.ResponseUserDetail(u.firstName, u.lastName, u.email, u.phone) from User u where 1 = 1");
        if (StringUtils.hasLength(search)) {
            sql.append(" and lower(u.firstName) like lower(:firstName)"); //các biến :firstName sẽ được map với biến bên ngoài khi setParameter
            sql.append(" or lower(u.lastName) like lower(:lastName)");
            sql.append(" or lower(u.email) like lower(:email)");
            // search by firstName, lastName & email
        }
        // sort a column
        if (StringUtils.hasLength(sortBy)) {
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                sql.append(String.format(" order by u.%s %s", matcher.group(1), matcher.group(3)));
            }
        }

        Query query = entityManager.createQuery(sql.toString());
        if (StringUtils.hasLength(search)) {
            query.setParameter("firstName", String.format("%%%s%%", search));
            query.setParameter("lastName", String.format("%%%s%%", search));
            query.setParameter("email", String.format("%%%s%%", search));
        }
            query.setFirstResult(pageNo); // xác định vị trí đầu tiên để lấy data
            query.setMaxResults(pageSize); // xác định số records tối đa có thể lấy

        // query couting totalPage
        StringBuilder sqlCount = new StringBuilder("SELECT count(*) from User u where 1 = 1 ");
// một cách map biến khác bằng index
        if (StringUtils.hasLength(search)){
            sqlCount.append(" and lower(u.firstName) like lower(?1)");
            sqlCount.append(" or lower(u.lastName) like lower(?2)");
            sqlCount.append(" or lower(u.email) like lower(?3)");
        }
        Query queryCount = entityManager.createQuery(sqlCount.toString());
        if (StringUtils.hasLength(search)){
            queryCount.setParameter(1, String.format("%%%s%%", search));
            queryCount.setParameter(2, String.format("%%%s%%", search));
            queryCount.setParameter(3, String.format("%%%s%%", search));
        }

        Long totalElement = (Long) queryCount.getSingleResult();
        Page<?> page = new PageImpl<Object>(query.getResultList(),PageRequest.of(pageNo, pageSize), totalElement);
        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(page.getTotalPages())
                .items(page.stream().toList())
                .build();
    }
}
