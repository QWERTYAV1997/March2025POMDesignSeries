pipeline 
{
    agent any
    
    tools{
        maven 'maven'
        }
        
        
    stages 
        {
          stage('Build') 
        {
            steps
            {
                 git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                 bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            post 
            {
                success
                {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        
        stage("Deploy to QA"){
            steps{
                echo("deploy to qa done")
            }
        }  
                
        stage('Regression Automation Tests') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/QWERTYAV1997/March2025POMDesignSeries.git'
                    bat "mvn clean test -Dsurefire.suiteXmlFiles=src/tests/resources/testRunners/testng_regression.xml -Denv=qa"
                    
                }
            }
        }
                 
        stage('Publish Allure Reports') {
           steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: '/allure-results']]
                    ])
                }
            }
        }
        
        stage("Deploy to Stage"){
            steps{
                echo("deploy to Stage")
            }
        }
        
        stage('Sanity Automation Test') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/QWERTYAV1997/March2025POMDesignSeries.git'
                    bat "mvn clean test -Dsurefire.suiteXmlFiles=src/tests/resources/testRunners/testng_sanity.xml -Denv=stage"
                }
            }
        }
        
        stage("Deploy to PROD"){
            steps{
                echo("deploy to PROD")
            }
        }

        stage('Sanity Automation Test on PROD') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/QWERTYAV1997/March2025POMDesignSeries.git'
                    bat "mvn clean test -Dsurefire.suiteXmlFiles=src/tests/resources/testRunners/testng_sanity.xml -Denv=prod"
                }
            }
        }
    }
}