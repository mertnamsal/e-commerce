package com.ecommerce.utility;

import com.ecommerce.exception.EErrorType;
import com.ecommerce.exception.ProductException;
import com.ecommerce.model.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public class ServiceManagerImpl <T extends BaseEntity,ID> implements IServiceManager<T,ID>{
    private final JpaRepository<T,ID> repository;

    @Override
    public T save(T t) {
        t.setCreateDate(LocalDateTime.now().toString());
        t.setUpdateDate(LocalDateTime.now().toString());
        t.setState(true);
        return repository.save(t);
    }

    @Override
    public T update(T t) {
        t.setUpdateDate(LocalDateTime.now().toString());
        return repository.save(t);
    }

    @Override
    public void delete(T t) {
        repository.delete(t);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T findById(ID id) {
        Optional<T> t = repository.findById(id);
        if(t.isEmpty()){
            throw new ProductException(EErrorType.PRODUCT_NOT_FOUND);
        }
        return t.get();
    }
}

