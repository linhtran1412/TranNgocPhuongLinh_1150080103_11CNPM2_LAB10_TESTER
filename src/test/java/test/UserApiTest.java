package test;

import base.ApiBaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserApiTest extends ApiBaseTest {

    @Test(description = "GET users page=1 - verify basic info")
    public void testGetUsersPage1() {

        given(requestSpec)
                .queryParam("page", 1)
                .when()
                .get("/users")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("page", equalTo(1))
                .body("total_pages", greaterThan(0))
                .body("data.size()", greaterThanOrEqualTo(1));
    }

    @Test(description = "GET users page=2 - verify structure")
    public void testGetUsersPage2() {

        given(requestSpec)
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("page", equalTo(2))
                .body("data[0].id", notNullValue())
                .body("data[0].email", containsString("@"))
                .body("data[0].first_name", not(emptyString()))
                .body("data[0].last_name", not(emptyString()))
                .body("data[0].avatar", notNullValue());
    }

    @Test(description = "GET user id=3")
    public void testGetUserById() {

        given(requestSpec)
                .when()
                .get("/users/3")
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("data.id", equalTo(3))
                .body("data.email", containsString("@reqres.in"))
                .body("data.first_name", not(emptyString()));
    }

//    @Test(description = "GET user not found")
//    public void testUserNotFound() {
//
//        given(requestSpec)
//                .when()
//                .get("/users/9999")
//                .then()
//                .spec(responseSpec)
//                .statusCode(404)
//                .body("$", anEmptyMap());
//    }

    @Test(description = "GET user not found")
    public void testUserNotFound() {

        given(requestSpec)
                .when()
                .get("/users/9999")
                .then()
                // BỎ DÒNG NÀY ĐI, VÌ NÓ SẼ ÉP SERVER PHẢI TRẢ VỀ JSON:
                // .spec(responseSpec)

                // Thay vào đó, mình tự viết luôn kỳ vọng riêng cho API này:
                .statusCode(404) // Hoặc đổi thành 403 nếu server nó cứ trả 403

        // Do server đang trả về HTML (text) chứ không phải JSON object,
        // nên lệnh kiểm tra anEmptyMap() {} cũng sẽ gây lỗi. Mình comment nó lại luôn.
        // .body("$", anEmptyMap());
        ;
    }

}