# Examples of FFmpeg framework

* serial implementation
```$xslt
public class Example extends Clipper<InputStream>
{
    @Main(id=1)
    public String method1(FFmpeg ffmpeg) {
        
        return <string result>
    }
    @Serial(id=1, index = 1)
    public void method2(String result) {
        return <InputStream as result>
    }
}
```

* parallel execution
```$xslt
public class Example extends Clipper<InputStream>
{
    @Main(id=1)
    public String method1(FFmpeg ffmpeg) {
        
        return <InputStream> 
    }
    @Main(id=2)
    public void method2(String result) {
        return <InputStream>
    }
}
new Example().start().getResult() // wait until get multiple result
new Example().start().tryResult() // return result at once (may not get any result)
```
# Rough workflow
![flow image](./doc/programChart.png)