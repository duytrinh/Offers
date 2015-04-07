//
//  MenuTableViewController.m
//  DemoOfferSDK
//
//  Created by TTA-Brown-005 on 2/3/15.
//  Copyright (c) 2015 TTA-Brown-005. All rights reserved.
//

#import "MenuTableViewController.h"
#import "SWRevealViewController.h"
#import "Constant.h"

@interface MenuTableViewController ()

@end

@implementation MenuTableViewController{
    NSArray *menuList;

}

- (void)viewDidLoad {
    [super viewDidLoad];
    menuList = @[FACEBOOK, TWITTER, NOTIFICATION];
    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
    
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {

    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {

    // Return the number of rows in the section.
    return menuList.count;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 75;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {

    
    // Configure the cell...
    NSString *CellIdentifier = [menuList objectAtIndex:indexPath.row];
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
    if (cell == nil)
    {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
        UISwitch* switchBtn = [[UISwitch alloc] init];
        switchBtn.tag = indexPath.row;
        [switchBtn addTarget:self action:@selector(onSwitchBtn:) forControlEvents:UIControlEventValueChanged];
        cell.accessoryView = switchBtn;
    }
//    if( cell == nil ) {
//        cell = [[UITableViewCell alloc] initWithFrame:CGRectZero];
//        cell.selectionStyle = UITableViewCellSelectionStyleNone;
//        UISwitch *switchView = [[UISwitch alloc] initWithFrame:CGRectZero];
//        cell.accessoryView = switchView;
//        [switchView setOn:NO animated:NO];
//        [switchView addTarget:self action:@selector(switchChanged:) forControlEvents:UIControlEventValueChanged];
//    }
    return cell;
}

#pragma - Advance functions
-(void)onSwitchBtn:(id)sender{
    UISwitch *switcher = (UISwitch*)sender;
}

/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath {
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath {
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    } else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath {
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath {
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
