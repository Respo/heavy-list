
Heavy list in Respo, to test performance
----

Built for https://github.com/krausest/js-framework-benchmark

Demo http://repo.respo.site/heavy-list/

To compile and deploy:

```bash
boot build-advanced
export boot_deps=`boot show -c`
planck -c $boot_deps:src/ -i render.cljs
boot rsync
```

### Develop

Workflow https://github.com/mvc-works/stack-workflow

### License

MIT
