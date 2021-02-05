package com.jim.learn.serviceprovider.graphQL;

import com.google.common.collect.ImmutableMap;
import com.jim.learn.serviceprovider.po.ProductDTO;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class GraphQLDataFetchers {

    /**
     * 书籍数据信息
     */
    private static List<Map<String, String>> books = Arrays.asList(
            ImmutableMap.of("id", "book-1",
                    "name", "Harry Potter and the Philosopher's Stone",
                    "pageCount", "223",
                    "authorId", "author-1"),
            ImmutableMap.of("id", "book-2",
                    "name", "Moby Dick",
                    "pageCount", "635",
                    "authorId", "author-2"),
            ImmutableMap.of("id", "book-3",
                    "name", "Interview with the vampire",
                    "pageCount", "371",
                    "authorId", "author-3")
    );

    /**
     * 书籍的作者信息
     */
    private static List<Map<String, String>> authors = Arrays.asList(
            ImmutableMap.of("id", "author-1",
                    "firstName", "Joanne",
                    "lastName", "Rowling"),
            ImmutableMap.of("id", "author-2",
                    "firstName", "Herman",
                    "lastName", "Melville"),
            ImmutableMap.of("id", "author-3",
                    "firstName", "Anne",
                    "lastName", "Rice")
    );

    private List<ProductDTO> products = Arrays.asList(
            new ProductDTO(1, "苹果", "非常香甜的苹果", 23.00),
            new ProductDTO(1, "香蕉", "香蕉超人", 13.00),
            new ProductDTO(1, "栗子", "举个栗子", 31.00),
            new ProductDTO(1, "西瓜", "大西瓜", 17.00),
            new ProductDTO(1, "菠萝", "酸菠萝", 21.00)

    );



    public DataFetcher getAllBooks(){

        return environment -> {

            Map<String, Object> arguments = environment.getArgument("book");
            //Book book = JSON.parseObject(JSON.toJSONString(arguments), Book.class);
            return books;
        };
    }

    public DataFetcher getBookByIdDataFetcher(){

        //dataFetchingEnvironment 封装了查询中带有的参数
        return dataFetchingEnvironment -> {

            String bookId = dataFetchingEnvironment.getArgument("id");
            return books.stream()
                    .filter(book -> book.get("id").equals(bookId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getAuthorDataFetcher(){

        //这里因为是通过Book查询Author数据的子查询，所以dataFetchingEnvironment.getSource()中封装了Book对象的全部信息
        //即GraphQL中每个字段的Datafetcher都是以自顶向下的方式调用的，父字段的结果是子Datafetcherenvironment的source属性。
        return dataFetcherEnvironment -> {

            Map<String, String> book = dataFetcherEnvironment.getSource();
            String authorId = book.get("authorId");
            return authors.stream()
                    .filter(authors -> authors.get("id").equals(authorId))
                    .findFirst()
                    .orElse(null);

        };
    }

    public DataFetcher productsDataFetcher(){

        return dataFetchingEnvironment -> {

            //DatabaseSecurityCtx ctx = environment.getContext();
            List<ProductDTO> products;
            String match = dataFetchingEnvironment.getArgument("match");

            System.out.println("the match is " + match);

            if (match != null) {
                products = fetchAllProductsFromDatabase();
            } else {
                products = fetchAllProductsFromDatabase();
            }
            return products;
        };
    }

    private List<ProductDTO> fetchAllProductsFromDatabase(){

        return products;
    }
}
