echo "# notebook" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin git@github.com:xuwenrui/notebook.git
git push -u origin main

添加多个远程仓库
 git remote add mirror git@github.com:xuwenrui/notebook.git

 git remote -v
mirror  git@github.com:xuwenrui/notebook.git (fetch)
mirror  git@github.com:xuwenrui/notebook.git (push)
origin  https://gitee.com/manrayhsu/notebook.git (fetch)
origin  https://gitee.com/manrayhsu/notebook.git (push)


修改仓库地址
```
git remote set-url origin 新的URL
```