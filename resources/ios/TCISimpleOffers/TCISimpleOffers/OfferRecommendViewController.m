//
//  OfferRecommendViewController.m
//  DemoOfferSDK
//
//  Created by TTA-Brown-005 on 2/2/15.
//  Copyright (c) 2015 TTA-Brown-005. All rights reserved.
//

#import "OfferRecommendViewController.h"
#import "SimpleTableViewCell.h"
#import <OffersKit/OffersKit.h>
#import "RecommendDetailsViewController.h"
#import "Constant.h"

@implementation OfferRecommendViewController
@synthesize itemList;
@synthesize table_Recommend;

- (void)viewDidLoad{
    [super viewDidLoad];
    [super viewDidAppear:YES];
    self.navigationItem.title = @"Recommend";
    // Remove header spaces while Back button clicked.
    self.automaticallyAdjustsScrollViewInsets = NO;
    OffersKitStatusCode code;
    itemList = [OffersRecommendation recommendationsWithStatus:&code];
    NSLog(@"StatusCode: %lu", (unsigned long)code );
    for( OffersRecommendation* item in itemList ) {
        [item loadImagesWithCallbackBlock:^( BOOL result ) {
            dispatch_async(dispatch_get_main_queue(), ^{
                NSIndexPath* indexPath = [NSIndexPath indexPathForRow:[itemList indexOfObject:item] inSection:0];
                SimpleTableViewCell* cell = (SimpleTableViewCell*)[self.table_Recommend cellForRowAtIndexPath:indexPath];
                [cell.couponImage setImage:item.thumbnailImage];
                [self.table_Recommend reloadData];
            });
        }];
    }
}

-(void)viewWillAppear:(BOOL)animated{
    self.title = @"Recommend";
}

#pragma mark - Table view datasources

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    static NSString *cellIdentifier = @"SimpleTableCell";
    
    SimpleTableViewCell *cell = (SimpleTableViewCell *)[tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if (cell == nil)
    {
        NSArray *nib = [[NSBundle mainBundle] loadNibNamed:@"SimpleTableCell" owner:self options:nil];
        cell = [nib objectAtIndex:0];
    }
    
    // Configure the cell...
    OffersRecommendation* recommendationInfo = [itemList objectAtIndex:indexPath.row];
    cell.couponTitle.text = recommendationInfo.name;
    cell.couponContent.text = recommendationInfo.mainDescription;
    cell.couponImage.image = recommendationInfo.thumbnailImage;
    return cell;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [self.itemList count];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
}

-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath{
    return 90;
}

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    [self performSegueWithIdentifier:@"detailsRecommend" sender:indexPath];
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    self.title = @"Back";
    if ([[segue identifier] isEqual:@"detailsRecommend"]) {
        NSIndexPath *indexPath = sender;
        OffersRecommendation *offers = [itemList objectAtIndex:indexPath.row];
        
        [[segue destinationViewController] setOffers:offers];
    }
}
@end
