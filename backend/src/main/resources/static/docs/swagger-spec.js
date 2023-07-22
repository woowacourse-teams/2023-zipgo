window.swaggerSpec={
  "openapi" : "3.0.1",
  "info" : {
    "title" : "집사의고민 API Docs",
    "description" : "집사의고민 API 명세서",
    "version" : "1.0.0"
  },
  "servers" : [ {
    "url" : "http://localhost:8080"
  } ],
  "tags" : [ ],
  "paths" : {
    "/pet-foods" : {
      "get" : {
        "tags" : [ "pet-foods" ],
        "summary" : "식품 목록 조회 성공",
        "description" : "모든 반려동물 식품을 조회합니다.",
        "operationId" : "get-pet-foods식품 목록 조회 성공식품 목록 조회 실패",
        "parameters" : [ {
          "name" : "keyword",
          "in" : "query",
          "description" : "식품 조회 API",
          "required" : false,
          "schema" : {
            "type" : "string"
          }
        } ],
        "responses" : {
          "200" : {
            "description" : "200",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/GetPetFoodResponse"
                },
                "examples" : {
                  "식품 목록 조회 성공" : {
                    "value" : "{\n  \"foodList\" : [ {\n    \"id\" : 1,\n    \"imageUrl\" : \"https://avatars.githubusercontent.com/u/94087228?v=4\",\n    \"name\" : \"[고집] 돌아온 배배\",\n    \"purchaseUrl\" : \"https://github.com/woowacourse-teams/2023-zipgo\"\n  }, {\n    \"id\" : 2,\n    \"imageUrl\" : \"https://avatars.githubusercontent.com/u/76938931?v=4\",\n    \"name\" : \"[고집] 갈비 맛 모밀\",\n    \"purchaseUrl\" : \"https://github.com/woowacourse-teams/2023-zipgo\"\n  } ]\n}"
                  },
                  "get-pet-foods" : {
                    "value" : "{\n  \"foodList\" : [ {\n    \"id\" : 1,\n    \"imageUrl\" : \"https://avatars.githubusercontent.com/u/94087228?v=4\",\n    \"name\" : \"[고집] 돌아온 배배\",\n    \"purchaseUrl\" : \"https://github.com/woowacourse-teams/2023-zipgo\"\n  }, {\n    \"id\" : 2,\n    \"imageUrl\" : \"https://avatars.githubusercontent.com/u/76938931?v=4\",\n    \"name\" : \"[고집] 갈비 맛 모밀\",\n    \"purchaseUrl\" : \"https://github.com/woowacourse-teams/2023-zipgo\"\n  } ]\n}"
                  }
                }
              }
            }
          },
          "404" : {
            "description" : "404",
            "content" : {
              "application/json" : {
                "schema" : {
                  "$ref" : "#/components/schemas/pet-foods486549215"
                },
                "examples" : {
                  "식품 목록 조회 실패" : {
                    "value" : "{\n  \"message\" : \"이름이 존재하지 않는 키워드 인 키워드를 찾을 수 없습니다.\"\n}"
                  }
                }
              }
            }
          }
        }
      }
    }
  },
  "components" : {
    "schemas" : {
      "GetPetFoodResponse" : {
        "title" : "GetPetFoodResponse",
        "type" : "object",
        "properties" : {
          "foodList" : {
            "type" : "array",
            "description" : "반려동물 식품 리스트",
            "items" : {
              "type" : "object",
              "properties" : {
                "purchaseUrl" : {
                  "type" : "string",
                  "description" : "구매 링크"
                },
                "imageUrl" : {
                  "type" : "string",
                  "description" : "식품 이미지 url"
                },
                "name" : {
                  "type" : "string",
                  "description" : "식품 이름"
                },
                "id" : {
                  "type" : "number",
                  "description" : "식품 id"
                }
              }
            }
          }
        }
      },
      "pet-foods486549215" : {
        "type" : "object"
      }
    }
  }
}