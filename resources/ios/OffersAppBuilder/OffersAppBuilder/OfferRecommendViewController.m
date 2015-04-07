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
#import "RecommendationDetailsWebViewController.h"
#import "Constant.h"

@implementation OfferRecommendViewController
@synthesize itemList;
@synthesize table_Recommend;

- (void)viewDidLoad{
    [super viewDidLoad];
    [super viewDidAppear:YES];
    self.navigationItem.title = @"NEWS";
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
    self.title = @"NEWS";
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
    return 300;
}

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    
    OffersRecommendation* recommendationInfo = [itemList objectAtIndex:indexPath.row];
    // recommendationInfo.type == @"RecommendedUrl" get @"recommendation_url" 0r "RecommendedArticle" get @"recommendation_id
    
    if([recommendationInfo.type isEqualToString:@"RecommendedArticle"]){
        
        [self performSegueWithIdentifier:@"detailsRecommend" sender:indexPath];
        [tableView deselectRowAtIndexPath:indexPath animated:YES];
        
    } else if([recommendationInfo.type isEqualToString:@"RecommendedUrl"]){
        
        [self performSegueWithIdentifier:@"detailRecommendURL" sender:indexPath];
        [tableView deselectRowAtIndexPath:indexPath animated:YES];
        
    }
    
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender{
    self.title = @"Back";
    NSIndexPath *indexPath = sender;
    OffersRecommendation *offers = [itemList objectAtIndex:indexPath.row];
    
    if ([[segue identifier] isEqual:@"detailsRecommend"]) {
        
        [[segue destinationViewController] setOffers:offers];
    }
    else if([[segue identifier] isEqual:@"detailRecommendURL"]){
        
        [[segue destinationViewController] setStrURL: offers.content];
    }
        
}
@end
