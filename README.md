# ipfx.org-examples-android

Examples how to use (www.ipfx.org) animation interpolators for Android. Feel free to contribute!  
Demo on GooglePlay (https://play.google.com/store/apps/details?id=org.ipfx.example)

# Basic usage

1. go to (http://ipfx.org/)
2. create interpolator function
3. copy generated url (data is encoded in the url)
4. pass url as a parameter to Interpolator.parseUrl() method

```
import org.ipfx.Interpolator;

...
...

final Interpolator interpolator = Interpolator.parseUrl(urlData);

ObjectAnimator animator = ObjectAnimator.ofFloat(testButton, View.Y, startY, endY);
animator.setDuration(1000);

animator.setInterpolator(new TimeInterpolator() {
    @Override
    public float getInterpolation(float input) {
        return interpolator.calc(input);
    }
});

animator.start();
```
