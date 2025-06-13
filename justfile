default:
    echo 'Hello, world!'


alias ct := clean-test
clean-test: 
    ./gradlew clean && ./gradlew test --warning-mode all
