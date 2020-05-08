
  Pod::Spec.new do |s|
    s.name = 'CapacitorFacebookPlugin'
    s.version = '0.0.2'
    s.summary = 'capacitor plugin adapting to native facebook sdk'
    s.license = 'MIT'
    s.homepage = 'https://c.eyeteam.vn/etop/capacitor-facebook-plugin'
    s.author = 'Manh Lai'
    s.source = { :git => 'https://c.eyeteam.vn/etop/capacitor-facebook-plugin', :tag => s.version.to_s }
    s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    s.ios.deployment_target  = '11.0'
    s.dependency 'Capacitor'
    s.dependency 'FacebookCore'
    s.dependency 'FacebookLogin'
  end