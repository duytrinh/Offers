//
//  OffersGroupViewController.m
//  TCISimpleOffers
//
//  Created by TTA-Brown-005 on 2/25/15.
//  Copyright (c) 2015 TTA-Brown-005. All rights reserved.
//

#import "OffersGroupViewController.h"
#import "SimpleTableViewCell.h"
#import <OffersKit/OffersKit.h>

@interface OffersGroupViewController ()

@property(nonatomic,strong) NSArray* itemList;

@end

@implementation OffersGroupViewController
@synthesize tbl_Group;

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.navigationItem.title = @"GROUP";
    
    // Remove header spaces while Back button clicked.
    self.automaticallyAdjustsScrollViewInsets = NO;
    
    self.itemList = [OffersGroup groups];
    for( OffersGroup* group in self.itemList ) {
        [group loadImagesWithCallbackBlock:^(BOOL result) {
            dispatch_async(dispatch_get_main_queue(), ^{
                [self.tbl_Group reloadData];
            });
        }];
    }
}

-(void)viewWillAppear:(BOOL)animated{
    self.title = @"GROUP";
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [self.itemList count];
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 300;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellIdentifier = @"SimpleTableCell";
    SimpleTableViewCell* cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if( cell == nil ) {
        cell = [[SimpleTableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:cellIdentifier];
    }
    
    // Configure the cell...
    OffersGroup* groupInfo = [self.itemList objectAtIndex:indexPath.row];
    
    cell.couponTitle.text = [NSString stringWithFormat:@"%lu", (unsigned long)groupInfo.groupId];
    if (groupInfo.groupImage1 == nil) {
        cell.couponImage.image = [UIImage imageWithContentsOfFile:[[NSBundle mainBundle] pathForResource:@"no-photo" ofType:@"jpg"]];
    }else{
        cell.couponImage.image = groupInfo.groupImage1;
    }
    cell.couponContent.text = groupInfo.name;
    
    return cell;
}

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    [self performSegueWithIdentifier:@"segueOffersGroups" sender:indexPath];
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    self.title = @"Back";
    NSIndexPath *indexPath = sender;
    if ([[segue identifier] isEqual:@"segueOffersGroups"]) {
        OffersGroup *obj = [self.itemList objectAtIndex:indexPath.row];
        OffersKitStatusCode code;
        NSArray *targetCoupons = [OffersCoupon couponsForShops:@[obj] status:&code];
        [[segue destinationViewController] setItemList:targetCoupons];
    }
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
