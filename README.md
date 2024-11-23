# CPT111 CW3: A Simple Quiz System
The purpose of this assignment is to design and develop an application that can be used to facilitate
educational quizzes, allowing users to select topics of interests, take quizzes related to those topics,
and view their quiz scores on a personal dashboard. The primary aim is to create a user-friendly
and interactive platform that enhances the learning experience through topic selection and question
assignment.
## Existing problems
- Does this CW need a GUI?
  - JavaFX
- Do we need to redirection the err info to a log file?
- 关于错误处理 我现在是都传到主类里去了 我们可以写一个函数来专门处理报错
## Task distribution
### Main --pmc
- TBD
### Class userBank --lx
- constructor   
  - usersBank(String[] topics_list)
- Method
  - check_user(str name, str pwd) -> bool
  - check_user(str name) -> bool 该用户名是否存在
  - write_user(str name, str pwd) -> int
  - write_user_score(str name, str topic, double score) 
  - read_user_score() -> ArrayList\<String[]\>
  - read_topic_score() -> ArrayList\<String[]\>
### Class questionBank --cfy
- constructor   
- Method
  - get_random_question(str topic, str difficulty) -> Question
### --wd
- Class Hierarchy
  - Object
    - InputStream (implements Closeable)
      - FilterInputStream
        - XmlInputStream
    - IOUtilities (implements AppConstants)snake_case
    - Option
    - Question (implements AppConstants)
    - XmlEntity
- Interface Hierarchy
  - AppConstants
  - AppConstants.Model
- Enum Class Hierarchy
  - Object
    - Enum (implements Comparable, Constable, Serializable)
    - Difficulty
### report --zzy

## NOTE
- The class name follow Camel Case `camelCase`, while function/method and variable follow Snake Case `snake_case`
- Ensure the code works for ANY csv and xml file
