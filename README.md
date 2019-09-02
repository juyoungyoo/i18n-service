# Channel Backend 과제

## 언어 제한
JAVA, PYTHON, GO, NODEJS 중 하나의 언어를 선택해주시기 바랍니다.

## 프레임워크 제한
구현을 보기 위하여, 아래 명시된 프레임 워크들의 사용을 제한합니다.

- JAVA: Spring
- PYTHON: Django REST Framework
- NODEJS: 제한 없음

## I18N Service

### 1. Objective
- 번역 될 문장을 나타내는 Key를 중심으로 여러 언어로의 번역을 지원

### 2. Model
Key

| Field Name | Type    | Description                                 |
|------------|---------|---------------------------------------------|
| id         | Integer |                                             |
| name       | String  | Key의 이름으로 dot와 영어 소문자만 사용가능 (unique) |


Translation

| Field Name | Type    | Description                             |
|------------|---------|-----------------------------------------|
| id         | Integer |                                         |
| keyId      | String  | Key의 ID                                |
| locale     | String  | ISO 639 Alpha-2 형식. ko, en, ja만 유효   |
| value      | String  | 번역된 문장                               |

### 3. API Specification

- 모든 키 가져오기
    * API Endpoint: /keys
    * Method: GET
    * Query Parameter: 
        | Field Name | Type   | Description                        |
        |------------|--------|------------------------------------|
        | name       | String | Optional. 검색하고 싶은 key의 이름 |
    * Example Response: 
        ```
        {
            "keys": [
                {
                    "id" : 1,
                    "name" : "test.key.first.one"
                },  {
                    "id" : 2,
                    "name" : "test.key.second.one"
                }
            ]
        } 
        ``` 

- 키 추가하기
    * API Endpoint: /keys
    * Method: POST
    * Example Payload:
        ```
        {
            "name" : "test.key.first.one"
        } 
        ```
    * Example Response: 
         ```
        {
            "key": {
                "id" : 1,
                "name" : "test.key.first.one"
            }
        } 
        ```

- 키 수정하기
    * API Endpoint: /keys/{keyId}
    * Method: PUT
    * Example Payload:
        ```
        {
            "name" : "test.key.revised.first.one"
        } 
        ```
    * Example Response: 
         ```
        {
            "key": {
                "id" : 1,
                "name" : "test.key.revised.first.one"
            }
        } 
        ```

- 번역 추가하기
    * API Endpoint: /keys/{keyId}/translations/{locale}
    * Method: POST
    * Example Payload:
        ```
        {
            "value" : "It's first one."
        } 
        ```
    * Example Response: 
         ```
        {
            "translation": {
                "id": 1,
                "keyId": 1,
                "locale": "en",
                "value": "It's first one." 
            }
        } 
        ```

- 키의 모든 번역 확인하기
    * API Endpoint: /keys/{keyId}/translations
    * Method: GET
    * Example Response: 
         ```
        {
            "translations": [
                {
                    "id": 1,
                    "keyId": 1,
                    "locale": "en",
                    "value": "It's first one." 
                },
                {
                    "id": 2,
                    "keyId": 1,
                    "locale": "ko",
                    "value": "첫번째 것 입니다." 
                }
            ]
        } 
        ```

- 키의 특정 언어 번역 확인하기
    * API Endpoint: /keys/{keyId}/translations/{locale}
    * Method: GET
    * Example Response: 
         ```
        {
            "translation": {
                "id": 1,
                "keyId": 1,
                "locale": "en",
                "value": "It's first one." 
            }
        } 
        ```

- 키의 특정 언어 번역 수정하기
    * API Endpoint: /keys/{keyId}/translations/{locale}
    * Method: PUT
    * Example Payload:
        ```
        {
            "value" : "It's revised first one."
        } 
        ```
    * Example Response: 
         ```
        {
            "translation": {
                "id": 1,
                "keyId": 1,
                "locale": "en",
                "value": "It's revised first one." 
            }
        } 
        ```

- Language detect
    * API Endpoint: /language_detect
    * Method: GET
    * Query Parameter: 
        | Field Name | Type   | Description                        |
        |------------|--------|------------------------------------|
        | message    | String | Required. String to find locale.   |
    * Example Response:
        ```
        {
          "locale": "en"
        }
        ```
    * https://detectlanguage.com/ 의 API를 사용해주시면 됩니다. (다른 API를 활용하셔도 됩니다.)