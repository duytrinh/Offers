//
//  OffersViewController.m
//  DemoOfferSDK
//
//  Created by TTA-Brown-005 on 2/3/15.
//  Copyright (c) 2015 TTA-Brown-005. All rights reserved.
//

#import "OffersViewController.h"
#import "SimpleTableViewCell.h"
#import "DetailsOffersViewController.h"
#import "RecommendDetailsViewController.h"
#import <OffersKit/OffersKit.h>

@interface OffersViewController ()

@property(nonatomic,strong) NSArray* itemList;

@end

@implementation OffersViewController
@synthesize tbl_Offers;

- (void)viewDidLoad {
    [super viewDidLoad];
    
    // Do any additional setup after loading the view.
    self.navigationItem.title = @"OFFERs";
    
    // Remove header spaces while Back button clicked.
    self.automaticallyAdjustsScrollViewInsets = NO;
    if (self.itemList.count == 0) {
        OffersKitStatusCode code;
        [self filterCoupon:[OffersOffer offersWithStatus:&code]];
    }
    
}

-(void)viewWillAppear:(BOOL)animated{
    self.title = @"OFFERs";
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Filter coupon
/**
 ** This function used for filter coupons which not recommendation coupons.
 **/
-(void)filterCoupon:(NSArray*)array{
    NSMutableArray *newArrayCoupons = [[NSMutableArray alloc] init];
    for (id object in array) {
        if ([object isKindOfClass:[OffersCoupon class]] == YES) {
            [newArrayCoupons addObject:object];
        }
    }
    self.itemList = newArrayCoupons;
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}   
*/

#pragma mark - Table view datasource

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    static NSString *cellIdentifier = @"SimpleTableCell";
    
    SimpleTableViewCell *cell = (SimpleTableViewCell *)[tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if (cell == nil)
    {
        NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"SimpleTableCell" owner:self options:nil];
        cell = [nib objectAtIndex:0];
    }
    
    // Configure the cell...
    id offerObject = [self.itemList objectAtIndex:indexPath.row];
    if([offerObject isKindOfClass:[OffersCoupon class]] == YES ) {
        OffersCoupon* coupon = (OffersCoupon*)offerObject;
//        NSLog(@"Type Code --- %d  ####### Code ---- %@",coupon.couponTypeCode, coupon.couponType);
        [coupon loadImagesWithCallbackBlock:^(BOOL result) {
            dispatch_async(dispatch_get_main_queue(), ^{
                if( cell.couponImage.image == nil ) {
                    if (coupon.thumbnailImage == nil) {
                        cell.couponImage.image = [UIImage imageWithContentsOfFile:[[NSBundle mainBundle] pathForResource:@"no-photo" ofType:@"jpg"]];
                    }else{
                        cell.couponImage.image = coupon.thumbnailImage;
                    }
                    cell.couponTitle.text = @"クーポン";
                    cell.couponContent.text = coupon.title;
                    [self.tbl_Offers reloadData];
                }
            });
        }];
    }
    return cell;

}



- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [self.itemList count];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 90;
}

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    [self performSegueWithIdentifier:@"detailsOffers" sender:indexPath];
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    self.title = @"Back";
    NSIndexPath *indexPath = sender;
    if ([[segue identifier] isEqual:@"detailsOffers"]) {
        OffersCoupon *coupon = (OffersCoupon *)[self.itemList objectAtIndex:indexPath.row];
        [[segue destinationViewController] setCoupon:coupon];
    }
}
@end
