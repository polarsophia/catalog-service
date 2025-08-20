custom_build(
    ref='library/catalog-service',
    command='gradlew bootBuildImage --imageName %EXPECTED_REF%',
    deps=['build.gradle', 'src']
)

k8s_yaml(['k8s/app/catalog-service.yml'])

k8s_resource('catalog-service', port_forwards=['8080'])
