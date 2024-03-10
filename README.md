# Budget Tracker App

## Setup
1. Navigate to the github main page and click on the green "Code" button in the upper right-hand corner.
2. On the "HTTPS" tab, copy the link. Then, open Terminal, `cd` to a directory where you want to keep the project code, and run:
   
    ```git clone [LINK YOU COPIED]```
   
   You will need your GitHub username and [Personal Access Token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens).

   ## Creating Branch for Local Work
   1. In order to create your own local branch to do local work navigate to the "Git" tab in the Android Studio menu on your computer it looks like this: <img width="554" alt="Screen Shot 2024-03-10 at 3 29 42 PM" src="https://github.com/lillieayer/budgettracker/assets/126836746/6b543b27-70fe-4b7e-ae33-27d3acae25ae">
   It should be in between "Tools" and "Window". Click on "Git" and then "Branches..." and click "Create New Branch". Name your branch after the task you're doing for example: "expense-tracker-page" or "login-auth". A new branch will be created from the "Head" so just the current branch you're currently on. Your android studio should now look like this whree the bottom right tab indicates which branch you're on or "checked out" onto.  
<img width="190" alt="Screen Shot 2024-03-10 at 3 34 11 PM" src="https://github.com/lillieayer/budgettracker/assets/126836746/9b47095c-1066-4553-a642-a4e482104f49">
2. anytime you want to switch to a new branch always use the command "fetch" to retrieve all current commits/changes and "checkout" which is the actual command that switches the current branch you're on. You can do this either in the terminal or in the Git drop down tab which contains these commands.
3. In the terminal you would enter it as such: ```git fetch && git checkout [branch-name] ```
4. Make sure to keep your branch updated by merging any changes from main by either using the Git drop down tab with Update Project tab or in the terminal using ```git fetch && git merge origin main ``` which will merge any changes from remote main with the code from your current branch that you're on.
5. Or you can use ```git fetch && git pull origin main ``` but this will copy all code from the remote main branch and replace all code in your current branch that you're on. Make sure when you pull from main that you have committed any changes you have made to your branch so they're not overwritten or lost. Commit work frequently and push after significant progress has been made that you're happy with. Try not to commit non-final work or work that contains errros and mistakes or compilation issues. 
6. Always work on a local branch (not main) so that we can track progress and liit merge conflicts when we're in the working stages. Once a feature or page has been thoroughly tested, works and is ready to be "published" make a pull request on the github remote repository. 

   ## Background Info
   This link contains good feature basics about user authentication, account profile and other features a budget app should contain.
   https://wesoftyou.com/fintech/budget-app-development-essential-features-and-monetization-strategies/
