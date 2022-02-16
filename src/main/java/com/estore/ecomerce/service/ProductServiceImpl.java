package com.estore.ecomerce.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.estore.ecomerce.domain.Category;
import com.estore.ecomerce.domain.Client;
import com.estore.ecomerce.domain.ImagePost;
import com.estore.ecomerce.domain.ImageProfile;
import com.estore.ecomerce.domain.Product;
import com.estore.ecomerce.repository.CategoryRepository;
import com.estore.ecomerce.repository.ClientRepository;
import com.estore.ecomerce.repository.ProductRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public ResponseEntity<?> saveProduct(Product product,
                               ArrayList<ImagePost> postImage,
                               ImageProfile image) {
        
        final ResponseEntity<?> messageFieldsEmpty = 
        new ResponseEntity<>("The fields Name, Price or Description can't be empty", 
        HttpStatus.NOT_ACCEPTABLE);          
        
        final ResponseEntity<?> messageValueDiscountInvalid =
        new ResponseEntity<>("The field discount is'nt acceptable", 
        HttpStatus.NOT_ACCEPTABLE);

        final ResponseEntity<?> messageClientNotExists =
        new ResponseEntity<>("The client not exists", 
        HttpStatus.NOT_FOUND);

        final ResponseEntity<?> messageCategoryNotExists =
        new ResponseEntity<>("One category or more is not exists", 
        HttpStatus.NOT_FOUND);

        if(controlFieldsEmpty(product)) return messageFieldsEmpty;
        
        if(controlValueOfDiscount(product.getDiscount()))
        return messageValueDiscountInvalid;
        
        setTheFieldsByDefault(product);

        //TODO ENCONTRAR LA MANERA DE OBTENER EL USUARIO LOGUEADO Y ASIGNARSELO AL PRODUCTO
        //eliminar las siguietes linea cuando se resuelva lo de arriba. 
        //if(controlClient(product)) return messageClientNotExists;
        product.setClient(null);


        if(controlCategories(product)) return messageCategoryNotExists;
        

        //Ultimo tratamos las imagenes.
        product.setImageProfile(image);          
        product.setImagePost(postImage);
        try {
            System.out.println(product.getImageProfile());
            System.out.println(product.getImagePost());
            System.out.println(product.getRegistration());
            System.out.println(product.getRating());
            productRepository.save(product);    
        } catch (Exception e) {
            e.printStackTrace();
        }              
        
        return new ResponseEntity<>("Product created succesfully!", 
        HttpStatus.OK);
    }

    private boolean controlCategories(Product product){
        List<Category> categories = (List<Category>) categoryRepository.findAll();

        if(product.getCategories().size() == 0) return false;
        if(categories.size() == 0) return true;

        
        //Comparamos la lista que se envió de cateogrias con la lista valida.
        return (getCategoriesValids(product.getCategories()).size() 
                != product.getCategories().size())? false:true;


    }

    private List<Category> getCategoriesValids(List<Category> categoriesRequest){
        Predicate<Category> condition = new Predicate<Category>(){
            @Override
            public boolean test(Category category){
                if(categoryRepository.findById(category.getId()).isPresent()){
                    //Si la categoria existe
                    return true;
                }
                    //Si no existe
                    return false;
            }
        };

        categoriesRequest = categoriesRequest.stream()
        .filter(condition)
        .collect(Collectors.toList());

        return categoriesRequest;
    }

    private boolean controlClient(Product product){
        Optional<Client> client = clientRepository.findById(product.getClient().getId());
        
        if(client.isPresent()){
            product.setClient(client.get());
            client.get().getProduct().add(product);
            return false;
        }else{
            return true;
        }
    }

    private void setTheFieldsByDefault(Product product){
        double newPrice;
        if(product.getDiscount() != 0.0){
            newPrice = product.getPrice()-((product.getPrice()*product.getDiscount())/100);
            product.setPrice(newPrice);
        }
        product.setRating(0.0);  
    }

    private boolean controlFieldsEmpty(Product product){
        return (
            product.getName() == null || 
            product.getName().trim().isEmpty() &&
            product.getPrice() == null &&
            product.getDescription() == null || 
            product.getDescription().trim().isEmpty()
        )? true : false;
    }

    private boolean controlValueOfDiscount(Double discount){
        return (
            discount > 100.0 ||
            discount < 0.0  
        )? true : false;
    }

    
}
