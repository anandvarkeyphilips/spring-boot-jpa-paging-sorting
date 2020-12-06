package com.bezkoder.spring.data.jpa.pagingsorting.repository;

import com.bezkoder.spring.data.jpa.pagingsorting.model.MyUser;
import com.bezkoder.spring.data.jpa.pagingsorting.model.QMyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;



import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

public interface MyUserRepository extends JpaRepository<MyUser, Long>, QuerydslPredicateExecutor<MyUser>, QuerydslBinderCustomizer<QMyUser> {
    @Override
    default public void customize(final QuerydslBindings bindings, final QMyUser root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.excluding(root.email);
    }

}
